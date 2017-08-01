package org.asocframework.dts.store.dal;

import java.util.Date;

/**
 * @author dhj
 * @version $Id: DtsActivityDO ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public class DtsActivityDO {

    private String txId;

    private String state;

    private String parentId;

    private Date createTime;

    private Date updateTime;

    private String name;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
