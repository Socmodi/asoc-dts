package org.asocframework.dts.test.dal.model;

/**
 * @author dhj
 * @version $Id: AssetSerial ,v 0.1 2017/6/5 dhj Exp $
 * @name
 */
public class AssetSerial {

    private int id;

    private String txId;

    private int amount;

    private String userId;

    private int bookkeeping;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getBookkeeping() {
        return bookkeeping;
    }

    public void setBookkeeping(int bookkeeping) {
        this.bookkeeping = bookkeeping;
    }
}
