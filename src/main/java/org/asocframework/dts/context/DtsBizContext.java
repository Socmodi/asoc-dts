package org.asocframework.dts.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dhj
 * @version $Id: DtsBizContext ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public class DtsBizContext {

    private boolean nexting;

    private String txId;

    private Map<String, Object> context = new HashMap();

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public boolean isNexting() {
        return nexting;
    }

    public void setNexting(boolean nexting) {
        this.nexting = nexting;
    }

}
