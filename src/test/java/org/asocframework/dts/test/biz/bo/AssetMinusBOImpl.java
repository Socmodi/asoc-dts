package org.asocframework.dts.test.biz.bo;

import org.asocframework.dts.context.DtsBizContext;
import org.asocframework.dts.test.dal.model.AssetSerial;
import org.asocframework.dts.test.dal.mapper.AssetSerialMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @author dhj
 * @version $Id: AssetMinusBOImpl ,v 0.1 2017/6/7 dhj Exp $
 * @name
 */
@Component("assetMinusBOTarget")
public class AssetMinusBOImpl implements AssetMinusBO{


    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private AssetSerialMapper assetSerialMapper;


    public void minus(String uid ,int amount,String txId){
        AssetSerial serial = new AssetSerial();
        serial.setTxId(txId);
        serial.setBookkeeping(-1);
        serial.setUserId(uid);
        serial.setAmount(amount);
        assetSerialMapper.insert(serial);
    }

    public boolean  commit(DtsBizContext businessContext){
        AssetSerial serial = new AssetSerial();
        serial.setTxId(businessContext.getTxId());
        return assetSerialMapper.update(serial)==1;
    }

    public boolean rollback(DtsBizContext businessContext){
        AssetSerial serial = new AssetSerial();
        serial.setTxId(businessContext.getTxId());
        return assetSerialMapper.delete(serial)==1;
    }


}
