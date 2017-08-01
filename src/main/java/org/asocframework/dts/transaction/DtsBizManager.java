package org.asocframework.dts.transaction;

import org.asocframework.dts.model.DtsActivity;
import org.asocframework.dts.store.DtsBizStore;
import org.asocframework.dts.store.DtsBizStoreDb;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author dhj
 * @version $Id: DtsBizAnnotation ,v 1.0 2017/7/12 dhj Exp $
 * @name
 */
public class DtsBizManager {

    private DtsBizStore dtsBizStore;

    private DataSource dataSource;

    private TransactionTemplate transactionTemplate;

    public DtsBizManager(DataSource dataSource) {
        this.dataSource = dataSource;
        this.transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
        this.transactionTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW");
    }

    public void start(final DtsActivity activity){
        dtsBizStore.addActivity(activity);
    }

    public DtsBizStore getDtsBizStore() {
        return dtsBizStore;
    }

    public void setDtsBizStore(DtsBizStore dtsBizStore) {
        this.dtsBizStore = dtsBizStore;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

}
