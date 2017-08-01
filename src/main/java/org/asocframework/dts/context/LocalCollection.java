package org.asocframework.dts.context;

import org.asocframework.dts.model.ActionInvoker;
import org.asocframework.dts.model.DtsAction;
import org.asocframework.dts.model.DtsBizAction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dhj
 * @version $Id: LocalCollection ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public class LocalCollection {


    private static final Map<String,DtsBizAction> DtsBizActions = new ConcurrentHashMap<>();

    private static final Map<String,ActionInvoker> acInvokers = new ConcurrentHashMap<>();

    private static final Map<DtsBizAction,DtsAction> actions = new ConcurrentHashMap<>();

    private static final Map<String,DtsBizAction> processes = new ConcurrentHashMap<>();

    public static boolean bzActionExist(String name){
        return DtsBizActions.containsKey(name);
    }

    public static DtsBizAction getDtsBizAction(String name){
        return DtsBizActions.get(name);
    }

    public static void cacheDtsBizAction(String name,DtsBizAction DtsBizAction){
        if(DtsBizActions.containsKey(name)){
            throw new RuntimeException("DtsBizAction name is "+name+" ,already exist!");
        }else {
            DtsBizActions.put(name,DtsBizAction);
        }
    }

    public static void cacheAction(DtsBizAction DtsBizAction,DtsAction action){
        actions.put(DtsBizAction,action);
    }

    public static void cacheActionInvoker(DtsBizAction DtsBizAction,ActionInvoker actionInvoker){
        if(acInvokers.containsKey(DtsBizAction)){
            return;
        }
        acInvokers.put(DtsBizAction.name(),actionInvoker);
        processes.put(actionInvoker.getCommitKey(),DtsBizAction);
        processes.put(actionInvoker.getRollbackKey(),DtsBizAction);
    }

    public static ActionInvoker getActionInvoker(String DtsBizAction){
        return acInvokers.get(DtsBizAction);
    }

    public static boolean  isProcessType(String name){
        return processes.containsKey(name);
    }

    public static DtsBizAction getDtsBizActionByProcess(String processName){
        return processes.get(processName);
    }

    public static DtsAction getAction(DtsBizAction DtsBizAction) {
        return actions.get(DtsBizAction);
    }

}
