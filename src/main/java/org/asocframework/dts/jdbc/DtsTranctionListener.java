package org.asocframework.dts.jdbc;

import org.asocframework.dts.context.DtsCurrent;
import org.asocframework.dts.context.DtsExecutor;
import org.asocframework.dts.model.DtsActivity;
import org.asocframework.dts.transaction.DtsBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import javax.annotation.Resource;

/**
 * @author dhj
 * @version $Id: DtsTranctionListener ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
@Component
public class DtsTranctionListener extends TransactionSynchronizationAdapter {

    @Autowired
    private DtsBizService dtsBizService;

    @Override
    public void afterCompletion(int status) {
        DtsActivity activity = DtsCurrent.getCurrentActivity();
        DtsExecutor.submit(new AfterCompletionWorker(activity,status,dtsBizService));
        /*完成事务一致性后，把事务活动结束，删除本地缓存记录*/
        finish(activity);
    }

    public void finish(DtsActivity activity ){
        DtsCurrent.clean();
        DtsCurrent.remove(activity.getTxId());
    }

}
