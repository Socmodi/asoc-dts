package org.asocframework.dts.transaction;

import com.alibaba.fastjson.JSON;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.asocframework.dts.context.DtsBizContext;
import org.asocframework.dts.context.DtsCurrent;
import org.asocframework.dts.context.LocalCollection;
import org.asocframework.dts.model.ActionInvoker;
import org.asocframework.dts.model.DtsAction;
import org.asocframework.dts.model.DtsActivity;
import org.asocframework.dts.model.DtsBizAction;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

/**
 * @author dhj
 * @version $Id: DtsBizAnnotation ,v 1.0 2017/7/12 dhj Exp $
 * @name
 */
public class DtsBizIntercepter implements MethodInterceptor {

    private DtsBizManager dtsBizManager;
    private DtsBizService dtsBizService;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        DtsBizAction dtsBizAction = method.getAnnotation(DtsBizAction.class);
        if(dtsBizAction!=null){
            return invokeAction(methodInvocation, dtsBizAction);
        }else {
            if(LocalCollection.isProcessType(methodInvocation.getThis().getClass().getName()+"."+method.getName())){
                return invokeProcessMethod(methodInvocation);
            }
        }
        return methodInvocation.proceed();
    }

    private Object invokeAction(MethodInvocation methodInvocation,DtsBizAction dtsBizAction) throws Throwable {
        DtsActivity activity = DtsCurrent.getCurrentActivity();
        ActionInvoker invoker ;
        if(!LocalCollection.bzActionExist(dtsBizAction.name())){
            invoker = new ActionInvoker(methodInvocation.getThis(),dtsBizAction);
            LocalCollection.cacheActionInvoker(dtsBizAction, invoker);
        }
        if(activity!=null){
            registerAction(activity, methodInvocation, dtsBizAction);
            if(dtsBizAction.nesting()){
                DtsBizContext dtsBizContext = new DtsBizContext();
                dtsBizContext.setTxId(activity.getTxId());
                dtsBizContext.setNexting(dtsBizAction.nesting());
                Method method = methodInvocation.getMethod();
                Type[] types = method.getGenericParameterTypes();
                for(int i=0;i<types.length;++i){
                    Class clazz = (Class) types[i];
                    if(clazz.getName().equals(DtsBizContext.class.getName())) {
                        methodInvocation.getArguments()[i] = dtsBizContext;
                        break;
                    }
                }
            }
            return methodInvocation.proceed();
        }else {
            DtsBizContext dtsBizContext = null;
            Method method = methodInvocation.getMethod();
            Type[] types = method.getGenericParameterTypes();
            for(int i=0;i<types.length;++i){
                Class clazz = (Class) types[i];
                if(clazz.getName().equals(DtsBizContext.class.getName())) {
                    dtsBizContext = (DtsBizContext) methodInvocation.getArguments()[i];
                    break;
                }
            }
            if(dtsBizContext==null){
                throw new RuntimeException("嵌套类型事务含有BusinessContext参数");
            }
            String txId = UUID.randomUUID().toString();
            activity = new DtsActivity(txId, JSON.toJSONString(dtsBizContext.getContext()));
            activity.setParentTxId(dtsBizContext.getTxId());
            activity.setNexting(true);
            activity.setName(dtsBizAction.name());
            //
            DtsCurrent.save(activity);
            dtsBizManager.getDtsBizStore().addActivity(activity);
            return methodInvocation.proceed();
        }
    }

    private Object invokeProcessMethod(MethodInvocation methodInvocation) throws Throwable {
        if(DtsCurrent.getCurrentActivity()!=null){
            return methodInvocation.proceed();
        }
        DtsBizContext businessContext = (DtsBizContext) methodInvocation.getArguments()[0];

        DtsBizAction dtsBizAction = LocalCollection.getDtsBizActionByProcess(methodInvocation.getThis().getClass().getName()+"."+methodInvocation.getMethod().getName());
        String processType = methodInvocation.getMethod().getName().endsWith(dtsBizAction.commitMethod())?"COMMIT":"ROLLBACK";
        if(dtsBizAction.nesting()){
            DtsActivity activity = dtsBizManager.getDtsBizStore().getActivityByPtxIdAndName(businessContext.getTxId(),dtsBizAction.name());
            if(activity!=null){
                DtsCurrent.setCurrentActivity(activity);
                List<DtsAction> list = dtsBizManager.getDtsBizStore().findActionByTxId(activity.getTxId());
                processActions(list,businessContext,processType,activity);
            }
            Object result = methodInvocation.proceed();
            finish(activity);
            return result;
        }else {
            return methodInvocation.proceed();
        }
    }

    private void processActions(List<DtsAction> actions,DtsBizContext dtsBizContext,String processType,DtsActivity activity){
        if(actions==null||actions.isEmpty()){
            /*不存在任何action*/
            return;
        }
        if("COMMIT".equals(processType)){
            txCommint(actions,dtsBizContext);
        }else {
            txRollback(actions, dtsBizContext);
        }
    }

    public void finish(DtsActivity activity ){
        DtsCurrent.clean();
        /*清除本地缓存activity*/
        DtsCurrent.remove(activity.getTxId());
        dtsBizManager.getDtsBizStore().deleteActivity(activity.getTxId());
    }


    public void txCommint(List<DtsAction> actions,DtsBizContext dtsBizContext){
        DtsAction action;
        for(int i = 0;i<actions.size();i++){
            action = actions.get(i);
            dtsBizContext.setNexting(action.getDtsBizAction().nesting());
            action.commit(dtsBizContext);
            dtsBizManager.getDtsBizStore().deleteAction(action);
        }
    }

    public void txRollback(List<DtsAction> actions,DtsBizContext dtsBizContext){
        DtsAction action;
        for(int i = 0;i<actions.size();i++){
            action = actions.get(i);
            dtsBizContext.setNexting(action.getDtsBizAction().nesting());
            action.rollback(dtsBizContext);
            dtsBizManager.getDtsBizStore().deleteAction(action);
        }
    }

    private void registerAction(DtsActivity activity,MethodInvocation invocation,DtsBizAction dtsBizAction){
        DtsAction action = null;
        ActionInvoker invoker = LocalCollection.getActionInvoker(dtsBizAction.name());
        action = new DtsAction(invoker);
        action.setTxId(activity.getTxId());
        String actionId = UUID.randomUUID().toString();
        action.setActionId(actionId);
        dtsBizManager.getDtsBizStore().addAction(action, activity.getTxId());
        /*祖册到本地缓存*/
        //DtsCurrent.registerActivityAction(activity, action);
    }

    public DtsBizService getDtsBizService() {
        return dtsBizService;
    }

    public void setDtsBizService(DtsBizService dtsBizService) {
        this.dtsBizService = dtsBizService;
        this.dtsBizManager = dtsBizService.getDtsBizManager();
    }


}
