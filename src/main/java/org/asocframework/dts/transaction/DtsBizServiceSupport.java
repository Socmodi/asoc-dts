package org.asocframework.dts.transaction;

import com.alibaba.fastjson.JSON;
import org.asocframework.dts.DbType;
import org.asocframework.dts.DtsException;
import org.asocframework.dts.context.DtsBizContext;
import org.asocframework.dts.context.DtsCurrent;
import org.asocframework.dts.context.LocalCollection;
import org.asocframework.dts.model.ActionInvoker;
import org.asocframework.dts.model.DtsAction;
import org.asocframework.dts.model.DtsActivity;
import org.asocframework.dts.model.DtsState;
import org.asocframework.dts.store.DtsBizStoreDb;
import org.asocframework.dts.store.dal.DtsActionDAO;
import org.asocframework.dts.store.dal.DtsActivityDAO;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author dhj
 * @version $Id: DtsBizAnnotation ,v 1.0 2017/7/12 dhj Exp $
 * @name
 */
public class DtsBizServiceSupport implements DtsBizService,InitializingBean {

    private DataSource dataSource;

    private String dbType;

    private DtsBizManager dtsBizManager;

    private String cron;

    private int retryTimes;


    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public DtsBizServiceSupport() {
    }

    public DtsBizServiceSupport(DataSource dataSource, String dbType) {
        this.dataSource = dataSource;
        this.dbType = dbType;
    }

    @Override
    public DtsActivity start(String bizNo, String bizType, Map<String, Object> attachs) {
        if(DtsCurrent.exists()){
            return DtsCurrent.getCurrentActivity();
        }else {
            String txId = UUID.randomUUID().toString();
            DtsActivity activity = new DtsActivity(txId, JSON.toJSONString(attachs));
            activity.setState(DtsState.INIT.getName());
            activity.setName(bizType);
            dtsBizManager.start(activity);
            DtsCurrent.setCurrentActivity(activity);
            return activity;
        }
    }

    @Override
    public void commit(List<DtsAction> actions, DtsBizContext dtsBizContext) {
        DtsAction  action;
        for(int i = 0;i<actions.size();i++){
            action = actions.get(i);
            action.commit(dtsBizContext);
            dtsBizManager.getDtsBizStore().deleteAction(action);
        }
    }

    @Override
    public void rollback(List<DtsAction> actions, DtsBizContext dtsBizContext) {
        DtsAction  action;
        for(int i = 0;i<actions.size();i++){
            action = actions.get(i);
            action.rollback(dtsBizContext);
            dtsBizManager.getDtsBizStore().deleteAction(action);
        }
    }

    @Override
    public void finishTx(DtsActivity dtsActivity) {
        dtsBizManager.getDtsBizStore().deleteActivity(dtsActivity.getTxId());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        checkDBParam();
        dtsBizManager = new DtsBizManager(this.dataSource);
        initDAO();
    }

    private void initDAO(){
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            Thread.currentThread().setContextClassLoader(classLoader);

            ClassPathResource[] resources = new ClassPathResource[]{
                    new ClassPathResource("mappers/action_mapper.xml"),new ClassPathResource("mappers/activity_mapper.xml")
            };
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(this.dataSource);
            sqlSessionFactoryBean.setMapperLocations(resources);
            sqlSessionFactoryBean.afterPropertiesSet();

            DtsActionDAO actionDAO = new DtsActionDAO();
            actionDAO.setSqlSessionFactory(sqlSessionFactoryBean.getObject());
            actionDAO.setDataSource(this.dataSource);

            DtsActivityDAO activityDAO = new DtsActivityDAO();
            activityDAO.setDataSource(this.dataSource);
            activityDAO.setSqlSessionFactory(sqlSessionFactoryBean.getObject());

            DtsBizStoreDb dtsBizStoreDb = new DtsBizStoreDb();
            dtsBizStoreDb.setDtsActionDAO(actionDAO);
            dtsBizStoreDb.setDtsActivityDAO(activityDAO);
            dtsBizManager.setDtsBizStore(dtsBizStoreDb);
            dtsBizStoreDb.setTransactionTemplate(dtsBizManager.getTransactionTemplate());
        }catch (Exception e){
            throw  new DtsException("dao init error",e);
        }finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }

    private void checkDBParam(){
        if(!DbType.MYSQL.getName().equalsIgnoreCase(dbType)){
            throw  new DtsException("DbType is error");
        }
        if(this.dataSource==null){
            throw  new DtsException("dataSource is null");
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public DtsBizManager getDtsBizManager() {
        return dtsBizManager;
    }

    public void setDtsBizManager(DtsBizManager dtsBizManager) {
        this.dtsBizManager = dtsBizManager;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public int getRetryTimes() {
        return retryTimes;
    }
}
