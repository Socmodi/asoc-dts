package org.asocframework.dts.store.dal;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dhj
 * @version $Id: DtsActivityDAO ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public class DtsActivityDAO extends SqlSessionDaoSupport {

    private DataSource dataSource;

    public int insert(DtsActivityDO activityDO){
        return getSqlSession().insert("asoc.dts.activity.insert",activityDO);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int delete(String txId) {
        return getSqlSession().insert("asoc.dts.activity.delete",txId);
    }

    public DtsActivityDO getActivityByPtxIdAndName(String parentId, String name) {
        Map map = new HashMap();
        map.put("parentId",parentId);
        map.put("name",name);
        return getSqlSession().selectOne("asoc.dts.activity.getActivityByPtxIdAndName",map);
    }

}
