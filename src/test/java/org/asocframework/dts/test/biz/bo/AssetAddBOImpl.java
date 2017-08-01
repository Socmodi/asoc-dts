package org.asocframework.dts.test.biz.bo;


import org.asocframework.dts.context.DtsBizContext;
import org.asocframework.dts.model.DtsBizAction;
import org.asocframework.dts.test.dal.model.AssetSerial;
import org.asocframework.dts.test.dal.mapper.AssetSerialMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @author dhj
 * @version $Id: AssetAddBOImpl ,v 0.1 2017/6/7 dhj Exp $
 * @name
 */
@Component("assetAddBOTarget")
public class AssetAddBOImpl implements AssetAddBO{


    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private AssetSerialMapper assetSerialMapper;

    @DtsBizAction(name = "asset_add",commitMethod = "commit" ,rollbackMethod = "rollback")
    public void add(String uid ,int amount,String txId){
        AssetSerial serial = new AssetSerial();
        serial.setTxId(txId);
        serial.setBookkeeping(1);
        serial.setUserId(uid);
        serial.setAmount(amount);
        assetSerialMapper.insert(serial);
    }
    @DtsBizAction(name = "",commitMethod = "commit" ,rollbackMethod = "rollback")
    public void test(){
        AssetSerial serial = new AssetSerial();
        assetSerialMapper.insert(serial);
    }

    public boolean  commit(DtsBizContext businessContext){
        final AssetSerial serial = new AssetSerial();
        serial.setTxId(businessContext.getTxId());
        return transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                return assetSerialMapper.update(serial)==1;
            }
        });
    }

    public boolean rollback(DtsBizContext businessContext){
        final AssetSerial serial = new AssetSerial();
        serial.setTxId(businessContext.getTxId());
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                return assetSerialMapper.delete(serial)==1;
            }
        });
        return true;
    }

}
