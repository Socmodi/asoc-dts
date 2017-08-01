package org.asocframework.dts.model;

import java.io.Serializable;

/**
 * @author dhj
 * @version $Id: DtsBizAnnotation ,v 1.0 2017/7/12 dhj Exp $
 * @name
 */
public class DtsActivity implements Serializable{

    private String parentTxId;

    private String txId;

    private String bizNo;

    private String bizType;

    private String attaches;

    private boolean nexting;

    private String state;

    private String name;

    public DtsActivity() {
    }

    public DtsActivity(String txId, String attaches) {
        this.txId = txId;
        this.attaches = attaches;
    }

    public String getParentTxId() {
        return parentTxId;
    }

    public void setParentTxId(String parentTxId) {
        this.parentTxId = parentTxId;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getAttaches() {
        return attaches;
    }

    public void setAttaches(String attaches) {
        this.attaches = attaches;
    }

    public boolean isNexting() {
        return nexting;
    }

    public void setNexting(boolean nexting) {
        this.nexting = nexting;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
