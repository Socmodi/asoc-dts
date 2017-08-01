package org.asocframework.dts.store.dal;

import java.util.Date;

/**
 * @author dhj
 * @version $Id: DtsActionDO ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public class DtsActionDO {

    private String actionId;

    private String txId;

    private String name;

    private String state;

    private Date createTime;

    private Date updateTime;

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
