package org.asocframework.dts.test.biz.bo;

import org.asocframework.dts.context.DtsBizContext;
import org.asocframework.dts.model.DtsBizAction;

/**
 * @author dhj
 * @version $Id: AssetAddBO ,v 0.1 2017/6/2 dhj Exp $
 * @name
 */
public interface AssetAddBO {

    @DtsBizAction(name = "asset_add",commitMethod = "commit" ,rollbackMethod = "rollback")
    public void add(String uid, int amount, String txId);

    @DtsBizAction(name = "",commitMethod = "commit" ,rollbackMethod = "rollback")
    public void test();

    public boolean  commit(DtsBizContext businessContext);

    public boolean rollback(DtsBizContext businessContext);

}
