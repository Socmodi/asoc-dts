package org.asocframework.dts.store;

import org.asocframework.dts.context.LocalCollection;
import org.asocframework.dts.model.ActionInvoker;
import org.asocframework.dts.model.DtsAction;
import org.asocframework.dts.model.DtsActivity;
import org.asocframework.dts.store.dal.DtsActionDAO;
import org.asocframework.dts.store.dal.DtsActionDO;
import org.asocframework.dts.store.dal.DtsActivityDAO;
import org.asocframework.dts.store.dal.DtsActivityDO;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dhj
 * @version $Id: DtsBizStoreDb ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public class DtsBizStoreDb implements DtsBizStore{

    private TransactionTemplate transactionTemplate;

    private DtsActionDAO dtsActionDAO;

    private DtsActivityDAO dtsActivityDAO;

    @Override
    public int addActivity(final DtsActivity activity) {
        return transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                DtsActivityDO activityDO = new DtsActivityDO();
                activityDO.setTxId(activity.getTxId());
                activityDO.setState(activity.getState());
                activityDO.setParentId(activity.getParentTxId());
                activityDO.setName(activity.getName());
                return dtsActivityDAO.insert(activityDO);
            }
        });
    }

    @Override
    public List<DtsAction> findActionByTxId(String txId) {
        List<DtsActionDO> dtsActionDOS = dtsActionDAO.findActionByTxId(txId);
        List<DtsAction> dtsActions = new ArrayList<>();
        for(DtsActionDO dtsActionDO:dtsActionDOS){
            DtsAction dtsAction = new DtsAction(LocalCollection.getActionInvoker(dtsActionDO.getName()));
            dtsAction.setActionId(dtsActionDO.getActionId());
            dtsAction.setTxId(dtsActionDO.getTxId());
            dtsActions.add(dtsAction);
        }
        return dtsActions;
    }

    @Override
    public DtsActivity getActivityByPtxIdAndName(String parentId, String name) {
        DtsActivityDO activityDO = dtsActivityDAO.getActivityByPtxIdAndName(parentId ,name);
        DtsActivity activity = null;
        if(activityDO!=null){
            activity  = new DtsActivity();
            activity.setName(activityDO.getName());
            activity.setParentTxId(activityDO.getParentId());
            activity.setTxId(activityDO.getTxId());
            activity.setState(activityDO.getState());
        }
        return activity;
    }

    @Override
    public int addAction(final DtsAction action,final String activityId) {
        return transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                DtsActionDO actionDO = new DtsActionDO();
                actionDO.setTxId(activityId);
                actionDO.setActionId(action.getActionId());
                actionDO.setName(action.getDtsBizAction().name());
                return dtsActionDAO.insert(actionDO);
            }
        });
    }

    @Override
    public void deleteAction(final DtsAction action) {
        transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                dtsActionDAO.delete(action.getTxId(),action.getActionId());
                return null;
            }
        });
    }

    @Override
    public void deleteActivity(final String txId) {
        transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                dtsActivityDAO.delete(txId);
                return null;
            }
        });
    }


    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public DtsActionDAO getDtsActionDAO() {
        return dtsActionDAO;
    }

    public void setDtsActionDAO(DtsActionDAO dtsActionDAO) {
        this.dtsActionDAO = dtsActionDAO;
    }

    public DtsActivityDAO getDtsActivityDAO() {
        return dtsActivityDAO;
    }

    public void setDtsActivityDAO(DtsActivityDAO dtsActivityDAO) {
        this.dtsActivityDAO = dtsActivityDAO;
    }
}
