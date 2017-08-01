package org.asocframework.dts.store;

import org.asocframework.dts.model.ActionInvoker;
import org.asocframework.dts.model.DtsAction;
import org.asocframework.dts.model.DtsActivity;

import java.util.List;

/**
 * @author dhj
 * @version $Id: DtsBizStore ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public interface DtsBizStore {


    int addActivity(DtsActivity activity);

    public List<DtsAction> findActionByTxId(String txId);

    public DtsActivity getActivityByPtxIdAndName(String parentId,String name);

    int addAction(DtsAction action, String activityId);

    void deleteAction(DtsAction action);

    void deleteActivity(String txId);

}
