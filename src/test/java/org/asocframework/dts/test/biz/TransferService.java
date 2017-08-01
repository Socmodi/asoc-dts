package org.asocframework.dts.test.biz;

import org.asocframework.dts.context.LocalCollection;
import org.asocframework.dts.jdbc.DtsTranctionListener;
import org.asocframework.dts.model.DtsActivity;
import org.asocframework.dts.test.biz.bo.AssetAddBO;
import org.asocframework.dts.test.biz.bo.AssetMinusBO;
import org.asocframework.dts.transaction.DtsBizService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import javax.annotation.Resource;

//import com.qccr.cosmos.common.spring.transaction.ReentrantTransactionBO;
//import com.qccr.cosmos.common.spring.transaction.TransactionCallback;

/**
 * @author dhj
 * @version $Id: TransferService ,v 0.1 2017/6/2 dhj Exp $
 * @name
 */
@Service
public class TransferService {

    @Resource
    private DtsBizService dtsBizService;
//
//    @Resource
//    private ReentrantTransactionBO reentrantTransactionBO;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private AssetAddBO assetAddBO;

    @Resource
    private AssetMinusBO assetMinusBO;

    @Resource
    private DtsTranctionListener dtsTranctionListener;
//
//    public void tranfer(){
//        reentrantTransactionBO.execute(new TransactionCallback<Object>() {
//            @Override
//            public Object doTransaction() {
//                TransactionSynchronizationManager.registerSynchronization(transactionListener);
//                Activity activity =businessControlService.start("123456","test",null);
//                assetAddBO.add("12345",10,activity.getTxId());
//                assetMinusBO.minus("54321",10,activity.getTxId());
//                return null;
//            }
//        });
//    }

    public void tranfer(){
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                TransactionSynchronizationManager.registerSynchronization(dtsTranctionListener);
                DtsActivity activity =dtsBizService.start("123456","test",null);
                assetAddBO.add("12345",10,activity.getTxId());
                assetMinusBO.minus("54321",10,activity.getTxId());
                return null;
            }
        });
        LocalCollection.bzActionExist("111");
    }

}
