package com.fc.v2.service.mysql.impl;

import com.fc.v2.dto.Pager;
import com.fc.v2.dto.QueryResult;
import com.fc.v2.dto.RestResponse;
import com.fc.v2.dto.Statement;
import com.fc.v2.mapper.mysql.MysqlServerMapper;
import com.fc.v2.service.mysql.JdbcService;
import com.fc.v2.service.mysql.StatementService;
import com.fc.v2.model.mysql.MysqlServer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: StatementServiceImpl
 * @Description:语句分析
 * @author: reisen
 * @date: 2024-10-16
 *
 */
@Service
public class StatementServiceImpl extends AbstractService implements StatementService
{
    // 内置数据库
    protected static final String BUILT_IN_SCHEMA = "'information_schema','mysql','performance_schema','sys','test',''";
    
    @Autowired
    private MysqlServerMapper mysqlServerMapper;
    
    @Autowired
    protected JdbcService jdbcService;
    

    /*
     * (non-Javadoc)
     *
     * @see cn.bqjr.dbeye.service.SqlExplainService#getExplain(java.lang. Long,
     * java.lang.String, java.lang.String)
     */
    @Override
    public Object getExplain(Long serverId, String schema, String sql)
    {
        RestResponse<List<Map<Object, Object>>> restResponse = new RestResponse<List<Map<Object, Object>>>();
        
        // 获取MySQL服务器信息
        MysqlServer mysqlServer = mysqlServerMapper.selectByPrimaryKey(serverId);
        if (mysqlServer == null)
        {
            restResponse.setMessage("mysqlServer is null");
            restResponse.setCode(1);
            return restResponse;
        }
        String host = mysqlServer.getHost();
        String port = String.valueOf(mysqlServer.getPort());
        String username = mysqlServer.getUsername();
        String password = mysqlServer.getPassword();
        String explainSql = "explain " + sql;
        QueryResult<List<Map<Object, Object>>> explainQueryResult =
            getQueryResult(host, port, schema, explainSql, username, password);
        
        // 如果数据库操作异常，则返回
        if (!explainQueryResult.isSuccess())
        {
            restResponse.setCode(500);
            restResponse.setMessage(explainQueryResult.getException());
            restResponse.setData(null);
            
            return restResponse;
        }
        
        List<Map<Object, Object>> explainList = explainQueryResult.getData();
        
        restResponse.setCode(0);
        restResponse.setMessage("success");
        restResponse.setData(explainList);
        
        return restResponse;
    }
    
    @Override
    public Object getTables(Long serverId, String statementExample)
    {
        return null;
    }
    
    @Override
    public Object getShowCreateTable(Long serverId, String schema, String tableName)
    {
        RestResponse<String> restResponse = new RestResponse<String>();
        // 获取该语句相关的MySQL服务器信息，主机、端口、用户名、密码
        MysqlServer mysqlServer = mysqlServerMapper.selectByPrimaryKey(serverId);
        if (mysqlServer == null)
        {
            restResponse.setMessage("mysqlServer is null");
            restResponse.setCode(1);
            return restResponse;
        }
        String host = mysqlServer.getHost();
        String port = String.valueOf(mysqlServer.getPort());
        String username = mysqlServer.getUsername();
        String password = mysqlServer.getPassword();
        String version = mysqlServer.getVer();
        // 连接到目标MySQL服务器，查询show create table语句
        String url = getDataSourceUrl(host, port, schema, username, password, version);
        String sql = "show create table " + tableName;
        QueryResult<List<Map<Object, Object>>> queryResult = jdbcService.queryForList(url, sql, username, password, version);
        List<Map<Object, Object>> data = queryResult.getData();
        if (!CollectionUtils.isEmpty(data))
        {
            String createTable = (String)data.get(0).get("Create Table");
            restResponse.setCode(0);
            restResponse.setMessage("success");
            restResponse.setData(createTable);
        }
        return restResponse;
    }
    
    @Override
    public Object getShowIndexFromTable(Long serverId, String schema, String tableName)
    {
        RestResponse<List<Map<Object, Object>>> restResponse = new RestResponse<List<Map<Object, Object>>>();
        // 获取该语句相关的MySQL服务器信息，主机、端口、用户名、密码
        MysqlServer mysqlServer = mysqlServerMapper.selectByPrimaryKey(serverId);
        if (mysqlServer == null)
        {
            restResponse.setMessage("mysqlServer is null");
            restResponse.setCode(1);
            return restResponse;
        }
        String host = mysqlServer.getHost();
        String port = String.valueOf(mysqlServer.getPort());
        String username = mysqlServer.getUsername();
        String password = mysqlServer.getPassword();
        String version = mysqlServer.getVer();
        // 连接到目标MySQL服务器，查询show create table语句
        String url = getDataSourceUrl(host, port, schema, username, password, version);
        String sql = "show index from " + tableName;
        QueryResult<List<Map<Object, Object>>> queryResult = jdbcService.queryForList(url, sql, username, password, version);
        List<Map<Object, Object>> data = queryResult.getData();
        if (!CollectionUtils.isEmpty(data))
        {
            restResponse.setCode(0);
            restResponse.setMessage("success");
            restResponse.setData(data);
        }
        return restResponse;
    }
    
    @Override
    public Object getStatementHistory(Long serverId, String orderBy)
    {
        RestResponse<List<Statement>> restResponse = new RestResponse<List<Statement>>();
        // 获取该语句相关的MySQL服务器信息，主机、端口、用户名、密码
        MysqlServer mysqlServer = mysqlServerMapper.selectByPrimaryKey(serverId);
        if (mysqlServer == null)
        {
            restResponse.setMessage("mysqlServer is null");
            restResponse.setCode(1);
            return restResponse;
        }
        String host = mysqlServer.getHost();
        String port = String.valueOf(mysqlServer.getPort());
        String username = mysqlServer.getUsername();
        String password = mysqlServer.getPassword();
        String version = mysqlServer.getVer();
        // 连接到目标MySQL服务器，查询events_statements_history表中的近期SQL语句
        String url = getDataSourceUrl(host, port, "performance_schema", username, password, version);
        String sql =
            "select t1.*,t2.* from (select DIGEST,max(SQL_TEXT) as SQL_TEXT,max(DIGEST_TEXT) as DIGEST_TEXT,MAX(EVENT_NAME) as EVENT_NAME,MAX(CURRENT_SCHEMA) AS CURRENT_SCHEMA from events_statements_history where CURRENT_SCHEMA not in ('sys','information_schema','mysql','test','performance_schema') and EVENT_NAME in ('statement/sql/select','statement/sql/update','statement/sql/insert','statement/sql/delete') GROUP BY DIGEST) t1 join (select DIGEST as d, COUNT_STAR,MIN_TIMER_WAIT,AVG_TIMER_WAIT,MAX_TIMER_WAIT,FIRST_SEEN,LAST_SEEN,SUM_LOCK_TIME,SUM_ROWS_AFFECTED,SUM_ROWS_SENT,SUM_ROWS_EXAMINED,SUM_CREATED_TMP_DISK_TABLES,SUM_CREATED_TMP_TABLES,SUM_SELECT_FULL_JOIN,SUM_SELECT_FULL_RANGE_JOIN,SUM_SELECT_RANGE,SUM_SELECT_RANGE_CHECK,SUM_SELECT_SCAN,SUM_SORT_MERGE_PASSES,SUM_SORT_RANGE,SUM_SORT_ROWS,SUM_SORT_SCAN,SUM_NO_INDEX_USED,SUM_NO_GOOD_INDEX_USED from events_statements_summary_by_digest) t2 on t1.DIGEST=t2.d ORDER BY "
                + orderBy;
        QueryResult<List<Map<Object, Object>>> queryResultByHistory =
            jdbcService.queryForList(url, sql, username, password, version);
        List<Map<Object, Object>> listHistory = queryResultByHistory.getData();
        
        List<Statement> statementList = new ArrayList<Statement>();
        for (Map<Object, Object> map : listHistory)
        {
            Statement statement = new Statement();
            statement.setDigest((String)map.get("DIGEST"));
            statement.setSqlText((String)map.get("SQL_TEXT"));
            statement.setDigestText((String)map.get("DIGEST_TEXT"));
            statement.setEventName((String)map.get("EVENT_NAME"));
            statement.setCurrentSchema((String)map.get("CURRENT_SCHEMA"));
            statement.setAvgTimerWait(((BigInteger)map.get("AVG_TIMER_WAIT")).longValue());
            statement.setCountStar(((BigInteger)map.get("COUNT_STAR")).longValue());
            statement.setFirstSeen((Date)map.get("FIRST_SEEN"));
            statement.setLastSeen((Date)map.get("LAST_SEEN"));
            statement.setMaxTimerWait(((BigInteger)map.get("MAX_TIMER_WAIT")).longValue());
            statement.setMinTimerWait(((BigInteger)map.get("MIN_TIMER_WAIT")).longValue());
            statement.setSumCreatedTmpDiskTables(((BigInteger)map.get("SUM_CREATED_TMP_DISK_TABLES")).longValue());
            statement.setSumCreatedTmpTables(((BigInteger)map.get("SUM_CREATED_TMP_TABLES")).longValue());
            statement.setSumLockTime(((BigInteger)map.get("SUM_LOCK_TIME")).longValue());
            statement.setSumNoGoodIndexUsed(((BigInteger)map.get("SUM_NO_GOOD_INDEX_USED")).longValue());
            statement.setSumNoIndexUsed(((BigInteger)map.get("SUM_NO_INDEX_USED")).longValue());
            statement.setSumRowsAffected(((BigInteger)map.get("SUM_ROWS_AFFECTED")).longValue());
            statement.setSumRowsExamined(((BigInteger)map.get("SUM_ROWS_EXAMINED")).longValue());
            statement.setSumRowsSent(((BigInteger)map.get("SUM_ROWS_SENT")).longValue());
            statement.setSumSelectFullJoin(((BigInteger)map.get("SUM_SELECT_FULL_JOIN")).longValue());
            statement.setSumSelectFullRangeJoin(((BigInteger)map.get("SUM_SELECT_FULL_RANGE_JOIN")).longValue());
            statement.setSumSelectRange(((BigInteger)map.get("SUM_SELECT_RANGE")).longValue());
            statement.setSumSelectRangeCheck(((BigInteger)map.get("SUM_SELECT_RANGE_CHECK")).longValue());
            statement.setSumSelectScan(((BigInteger)map.get("SUM_SELECT_SCAN")).longValue());
            statement.setSumSortMergePasses(((BigInteger)map.get("SUM_SORT_MERGE_PASSES")).longValue());
            statement.setSumSortRange(((BigInteger)map.get("SUM_SORT_RANGE")).longValue());
            statement.setSumSortRows(((BigInteger)map.get("SUM_SORT_ROWS")).longValue());
            statement.setSumSortScan(((BigInteger)map.get("SUM_SORT_SCAN")).longValue());
            statementList.add(statement);
        }
        
        restResponse.setCode(0);
        restResponse.setMessage("success");
        restResponse.setData(statementList);
        return restResponse;
        
    }
    
    @Override
    public Object getShowTableStatus(Long serverId, String schema, String table)
    {
        RestResponse<List<Map<Object, Object>>> restResponse = new RestResponse<List<Map<Object, Object>>>();
        // 获取该语句相关的MySQL服务器信息，主机、端口、用户名、密码
        MysqlServer mysqlServer = mysqlServerMapper.selectByPrimaryKey(serverId);
        if (mysqlServer == null)
        {
            restResponse.setMessage("mysqlServer is null");
            restResponse.setCode(1);
            return restResponse;
        }
        String host = mysqlServer.getHost();
        String port = String.valueOf(mysqlServer.getPort());
        String username = mysqlServer.getUsername();
        String password = mysqlServer.getPassword();
        // 连接到目标MySQL服务器，查询show create table语句
        String version = mysqlServer.getVer();
        String url = getDataSourceUrl(host, port, schema, username, password, version);
        String sql = "show table status like '" + table + "'";
        QueryResult<List<Map<Object, Object>>> queryResult = jdbcService.queryForList(url, sql, username, password, version);
        List<Map<Object, Object>> data = queryResult.getData();
        if (!CollectionUtils.isEmpty(data))
        {
            restResponse.setCode(0);
            restResponse.setMessage("success");
            restResponse.setData(data);
        }
        return restResponse;
    }
    
    @Override
    public Object executeSql(Long serverId, String schema, String sql)
    {
        RestResponse<List<Map<Object, Object>>> restResponse = new RestResponse<List<Map<Object, Object>>>();
        MysqlServer mysqlServer = mysqlServerMapper.selectByPrimaryKey(serverId);
        if (mysqlServer == null)
        {
            restResponse.setMessage("mysqlServer is null");
            restResponse.setCode(1);
            return restResponse;
        }
        String host = mysqlServer.getHost();
        String port = String.valueOf(mysqlServer.getPort());
        String username = mysqlServer.getUsername();
        String password = mysqlServer.getPassword();
        String version = mysqlServer.getVer();
        // 连接到目标MySQL服务器，查询show create table语句
        String url = getDataSourceUrl(host, port, schema, username, password, version);
        QueryResult<List<Map<Object, Object>>> queryResult = jdbcService.queryForList(url, sql, username, password, version);
        
        if (!queryResult.isSuccess())
        {
            restResponse.setCode(1);
            restResponse.setMessage(queryResult.getException());
            return restResponse;
        }
        
        restResponse.setCode(0);
        restResponse.setMessage("success");
        restResponse.setData(queryResult.getData());
        return restResponse;
    }
    
    @Override
    public Object getStatementSlow(Long serverId, String orderBy, Integer pageIndex, Integer pageSize)
    {
        RestResponse<Pager<List<Map<Object, Object>>>> restResponse = new RestResponse<>();
        MysqlServer mysqlServer = mysqlServerMapper.selectByPrimaryKey(serverId);
        if (mysqlServer == null)
        {
            restResponse.setMessage("mysqlServer is null");
            restResponse.setCode(1);
            return restResponse;
        }
        String host = mysqlServer.getHost();
        String port = String.valueOf(mysqlServer.getPort());
        String username = mysqlServer.getUsername();
        String password = mysqlServer.getPassword();
        String version = mysqlServer.getVer();
        Integer start = pageIndex * pageSize;
        Integer offset = pageSize;
        String sqlTotal = "select count(1) from mysql.slow_log where db NOT IN (" + BUILT_IN_SCHEMA + ")";
        Integer count = getQueryCount(host, port, sqlTotal, username, password, version);
        String sqlPager = "select * from mysql.slow_log where db NOT IN (" + BUILT_IN_SCHEMA + ") order by " + orderBy
            + " limit " + start + "," + offset;
        QueryResult<List<Map<Object, Object>>> slowLogQueryResult =
            getQueryResult(host, port, sqlPager, username, password, version);
        restResponse.setCode(0);
        List<Map<Object, Object>> rows = slowLogQueryResult.getData();
        Pager<List<Map<Object, Object>>> pager = new Pager<>();

        pager.setRows(rows);
        pager.setTotal(count);
        restResponse.setData(pager);
        restResponse.setMessage("success");
        return restResponse;
    }
    
}
