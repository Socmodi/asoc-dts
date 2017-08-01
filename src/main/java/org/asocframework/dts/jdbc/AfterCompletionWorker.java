package org.asocframework.dts.jdbc;

import org.asocframework.dts.context.DtsBizContext;
import org.asocframework.dts.context.DtsCurrent;
import org.asocframework.dts.model.ActionInvoker;
import org.asocframework.dts.model.DtsAction;
import org.asocframework.dts.model.DtsActivity;
import org.asocframework.dts.transaction.DtsBizService;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.springframework.transaction.support.TransactionSynchronization.STATUS_COMMITTED;
import static org.springframework.transaction.support.TransactionSynchronization.STATUS_ROLLED_BACK;

/**
 * @author dhj
 * @version $Id: AfterCompletionWorker ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public class AfterCompletionWorker implements Callable<Boolean>{

    private DtsActivity dtsActivity;

    private int txStatus;

    private DtsBizService dtsBizService;

    public AfterCompletionWorker(DtsActivity dtsActivity, int txStatus,DtsBizService dtsBizService) {
        this.dtsActivity = dtsActivity;
        this.txStatus = txStatus;
        this.dtsBizService = dtsBizService;
    }

    @Override
    public Boolean call() throws Exception {
        if(dtsActivity==null){
            return false;
        }
        if(dtsActivity.isNexting()){
            return false;
        }
        //LinkedList<DtsAction> actions = DtsCurrent.getActivityActions(dtsActivity);
        List<DtsAction> actions =  dtsBizService.getDtsBizManager().getDtsBizStore().findActionByTxId(dtsActivity.getTxId());
        DtsBizContext businessContext = new DtsBizContext();
        businessContext.setTxId(dtsActivity.getTxId());
        if (STATUS_COMMITTED==txStatus){
            dtsBizService.commit(actions,businessContext);
        }else if(STATUS_ROLLED_BACK==txStatus){
            dtsBizService.rollback(actions,businessContext);
        }
        dtsBizService.finishTx(dtsActivity);
        return true;
    }
}
