package org.asocframework.dts.test.biz.bo;

import org.asocframework.dts.context.DtsBizContext;
import org.asocframework.dts.model.DtsBizAction;

/**
 * @author dhj
 * @version $Id: AssetBO ,v 0.1 2017/6/2 dhj Exp $
 * @name
 */
public interface AssetMinusBO {

    @DtsBizAction(name = "asset_minus",commitMethod = "commit" ,rollbackMethod = "rollback")
    public void minus(String uid, int amount, String txId);

    public boolean  commit(DtsBizContext businessContext);

    public boolean rollback(DtsBizContext businessContext);

}
