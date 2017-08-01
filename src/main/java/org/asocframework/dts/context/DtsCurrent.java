package org.asocframework.dts.context;

import org.asocframework.dts.model.DtsAction;
import org.asocframework.dts.model.DtsActivity;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dhj
 * @version $Id: DtsCurrent ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public class DtsCurrent {

    private DtsCurrent() {
    }

    private static final ThreadLocal<DtsActivity> currentActivity = new ThreadLocal<>();

    public static final ConcurrentHashMap<String , DtsActivity> activitys = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<DtsActivity,LinkedList<DtsAction>> activityActions = new ConcurrentHashMap(128);



    public static LinkedList<DtsAction> getActivityActions(DtsActivity activity){
        return activityActions.get(activity);
    }

    public static void  registerActivityAction(DtsActivity activity, DtsAction action){
        LinkedList<DtsAction> linkedList = null;
        if((linkedList=activityActions.get(activity))!=null){
            linkedList.add(action);
        }else {
            linkedList  =  new LinkedList<>();
            linkedList.add(action);
            activityActions.put(activity,linkedList);
        }
    }

    public static void removeActivityActions(DtsActivity activity){
        activityActions.remove(activity);
    }

    public static boolean save(DtsActivity activity){
        if(activitys.containsKey(activity.getTxId())){
            return true;
        }else {
            //activitys.put(activity.getTxId(),activity);
            currentActivity.set(activity);
        }
        return true;
    }

    public static boolean exists(){
        return currentActivity.get()!=null;
    }

    public static void remove(String txId){
        activitys.remove(txId);
    }

    public static void setCurrentActivity(DtsActivity activity){
        currentActivity.set(activity);
    }

    public static DtsActivity getCurrentActivity(){
        return currentActivity.get();
    }

    public static void clean(){
        currentActivity.remove();
    }

}
