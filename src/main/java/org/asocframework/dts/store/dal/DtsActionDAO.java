package org.asocframework.dts.store.dal;

import org.asocframework.dts.model.DtsAction;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dhj
 * @version $Id: DtsActionDAO ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
public class DtsActionDAO extends SqlSessionDaoSupport {

    private DataSource dataSource;

    public int insert(DtsActionDO actionDO){
        return getSqlSession().insert("asoc.dts.action.insert",actionDO);
    }

    public int delete(String txId,String actionId){
        Map<String,String> map = new HashMap();
        map.put("txId",txId);
        map.put("actionId",actionId);
        return getSqlSession().delete("asoc.dts.action.delete",map);
    }


    public List<DtsActionDO> findActionByTxId(String txId){
        return getSqlSession().selectList("asoc.dts.action.findActionByTxId",txId);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
