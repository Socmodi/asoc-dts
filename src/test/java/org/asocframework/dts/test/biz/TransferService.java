package org.asocframework.dts.test.biz;

import org.asocframework.dts.context.LocalCollection;
import org.asocframework.dts.jdbc.DtsTranctionListener;
import org.asocframework.dts.model.DtsActivity;
import org.asocframework.dts.test.biz.bo.AssetAddBO;
import org.asocframework.dts.test.biz.bo.AssetMinusBO;
import org.asocframework.dts.test.dal.mapper.AssetSerialMapper;
import org.asocframework.dts.test.dal.model.AssetSerial;
import org.asocframework.dts.transaction.DtsBizService;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private AssetSerialMapper assetSerialMapper;
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


    public void read(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                transactionTemplate.execute(new TransactionCallback<Object>() {
                    @Override
                    public Object doInTransaction(TransactionStatus transactionStatus) {

                        System.out.println("read start:"+System.currentTimeMillis());
                        AssetSerial serial = new AssetSerial();
                        serial.setTxId("3333334");
                        serial.setBookkeeping(1);
                        serial.setUserId("333333");
                        serial.setAmount(1);
                        assetSerialMapper.update(serial);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //AssetSerial assetSerial = assetSerialMapper.find("2222222");
                        //System.out.println(assetSerial.getStatus());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("read start:"+System.currentTimeMillis());
                        List<AssetSerial> list = assetSerialMapper.select();
                        System.out.println(list.size());
                        System.out.println("read end:"+System.currentTimeMillis());
                        return null;
                    }
                });
            }
        }).start();

        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                System.out.println("update start:"+System.currentTimeMillis());
                AssetSerial serial = new AssetSerial();
                serial.setTxId("3333337");
                serial.setBookkeeping(1);
                serial.setUserId("333333");
                serial.setAmount(1);
                assetSerialMapper.insert(serial);
                //System.out.println("update size:"+assetSerialMapper.update(serial));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("update end:"+System.currentTimeMillis());
                return null;
            }
        });
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
