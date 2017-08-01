package org.asocframework.dts.model;

import org.asocframework.dts.context.DtsBizContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author dhj
 * @version $Id: DtsBizAnnotation ,v 1.0 2017/7/12 dhj Exp $
 * @name
 */
public class DtsAction {

    private DtsActivity dtsActivity;

    private Object target;

    private Method method;

    private DtsBizAction dtsBizAction;

    private Method rollback;

    private Method commit;

    private String actionId;

    private String txId;

    private ActionInvoker actionInvoker;

    public DtsAction(Object target, DtsBizAction dtsBizAction) {
        this.target = target;
        this.dtsBizAction = dtsBizAction;
        try {
            commit = target.getClass().getMethod(dtsBizAction.commitMethod(),new Class[]{DtsBizAction.class});
            rollback  = target.getClass().getMethod(dtsBizAction.rollbackMethod(),new Class[]{DtsBizAction.class});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public DtsAction(ActionInvoker invoker) {
        this.target = invoker.getTarget();
        this.commit = invoker.getCommit();
        this.rollback = invoker.getRollback();
        this.dtsBizAction = invoker.getDtsBizAction();
        this.actionInvoker = invoker;
    }


    public void commit(DtsBizContext dtsBizContext){
        actionInvoker.commit(dtsBizContext);
//        try {
//            commit.invoke(target,dtsBizContext);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }

    public  void rollback(DtsBizContext dtsBizContext){
        actionInvoker.rollback(dtsBizContext);
//        try {
//            rollback.invoke(target,dtsBizContext);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }

    public DtsActivity getDtsActivity() {
        return dtsActivity;
    }

    public void setDtsActivity(DtsActivity dtsActivity) {
        this.dtsActivity = dtsActivity;
    }

    public DtsBizAction getDtsBizAction() {
        return dtsBizAction;
    }

    public void setDtsBizAction(DtsBizAction dtsBizAction) {
        this.dtsBizAction = dtsBizAction;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getRollback() {
        return rollback;
    }

    public void setRollback(Method rollback) {
        this.rollback = rollback;
    }

    public Method getCommit() {
        return commit;
    }

    public void setCommit(Method commit) {
        this.commit = commit;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

}
