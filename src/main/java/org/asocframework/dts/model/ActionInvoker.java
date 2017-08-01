package org.asocframework.dts.model;

import org.asocframework.dts.context.DtsBizContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author dhj
 * @version $Id: ActionInvoker ,v 0.1 2017/6/6 dhj Exp $
 * @name
 */
public class ActionInvoker {

    private Object target;

    private Method method;

    private DtsBizAction dtsBizAction;

    private Method rollback;

    private Method commit;

    private String rollbackKey;

    private String commitKey;


    public ActionInvoker(Object target, DtsBizAction dtsBizAction) {
        this.target = target;
        this.dtsBizAction = dtsBizAction;
        try {
            commit = target.getClass().getMethod(dtsBizAction.commitMethod(),new Class[]{DtsBizContext.class});
            commitKey = target.getClass().getName()+"."+commit.getName();
            rollback  = target.getClass().getMethod(dtsBizAction.rollbackMethod(),new Class[]{DtsBizContext.class});
            rollbackKey = target.getClass().getName()+"."+rollback.getName();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public void commit(DtsBizContext businessContext){
        try {
            commit.invoke(target,businessContext);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public  void rollback(DtsBizContext businessContext){
        try {
            rollback.invoke(target,businessContext);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
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

    public DtsBizAction getDtsBizAction() {
        return dtsBizAction;
    }

    public void setDtsBizAction(DtsBizAction dtsBizAction) {
        this.dtsBizAction = dtsBizAction;
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

    public String getRollbackKey() {
        return rollbackKey;
    }

    public void setRollbackKey(String rollbackKey) {
        this.rollbackKey = rollbackKey;
    }

    public String getCommitKey() {
        return commitKey;
    }

    public void setCommitKey(String commitKey) {
        this.commitKey = commitKey;
    }
}
