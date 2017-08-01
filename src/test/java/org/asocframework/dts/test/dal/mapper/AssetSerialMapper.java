package org.asocframework.dts.test.dal.mapper;

import org.asocframework.dts.test.dal.model.AssetSerial;

/**
 * @author dhj
 * @version $Id: AssetSerial ,v 0.1 2017/6/5 dhj Exp $
 * @name
 */
public interface AssetSerialMapper {

    void insert(AssetSerial assetSerial);

    int  delete(AssetSerial assetSerial);

    int  update(AssetSerial assetSerial);

}
