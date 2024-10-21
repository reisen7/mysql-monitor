package com.fc.v2.service.monitor.impl;

import com.fc.v2.dto.QueryResult;
import com.fc.v2.service.monitor.JdbcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

/**
 * @ClassName: JdbcServiceImpl
 * @Description
 * @author: reisen
 * @date: 2024-10-16
 */
@Service
public class JdbcServiceImpl implements JdbcService {
    private final static Logger LOGGER = LoggerFactory.getLogger(JdbcServiceImpl.class);

    /*
     * (non-Javadoc)
     *
     * @see cn.bqjr.mysqleye.service.JdbcService#queryForList(java.lang.String,
     * java.lang.String)
     */
    @Override
    public QueryResult<List<Map<Object, Object>>> queryForList(String url, String sql, String user, String password, String version) {
        QueryResult<List<Map<Object, Object>>> queryResult = new QueryResult<List<Map<Object, Object>>>();
        Connection conn = null;
        try {
            conn = getConnection(url,user,password,version);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                List<Map<Object, Object>> list = resultSetToList(rs);
                // 操作成功，则将数据放入查询结果中
                queryResult.setSuccess(true);
                queryResult.setData(list);
                LOGGER.info("url:" + url + ",sql:" + sql);
            }
        } catch (Exception e) {
            // 数据操作异常，则将异常信息放入查询结果中
            queryResult.setSuccess(false);
            queryResult.setException(e.getMessage());
            // 打印日志
            String message = "url:" + url + ",sql:" + sql + ",exception:" + e.getMessage();
            LOGGER.error(message);
        } finally {
            closeConnection(conn);
        }
        return queryResult;
    }



    /*
     * (non-Javadoc)
     *
     * @see cn.bqjr.mysqleye.service.JdbcService#queryForCount(java.lang.String,
     * java.lang.String)
     */
    @Override
    public QueryResult<Integer> queryForCount(String url, String sql, String user, String password, String version) {
        QueryResult<Integer> queryResult = new QueryResult<Integer>();
        Integer count = 0;
        Connection conn = null;
        try {
            conn = getConnection(url,user,password,version);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                rs.next();
                count = Integer.valueOf(rs.getInt(1));
                queryResult.setSuccess(true);
                queryResult.setData(count);
                LOGGER.info("url:" + url + ",sql:" + sql);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            queryResult.setSuccess(false);
            queryResult.setException(e.getMessage());
        } finally {
            closeConnection(conn);
        }

        return queryResult;
    }



    @Override
    public QueryResult<Integer> executeSqlForBoolean(String url, String sql, String user, String password, String version) {
        QueryResult<Integer> queryResult = new QueryResult<Integer>();
        Connection conn = null;
        try {
            conn = getConnection(url,user,password,version);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                Integer executeResult = stmt.executeUpdate(sql);
                queryResult.setSuccess(true);
                queryResult.setData(executeResult);
                LOGGER.info("url:" + url + ",sql:" + sql);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            queryResult.setSuccess(false);
            queryResult.setException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return queryResult;

    }
    
    @Override
    public void executeSql(String url, String sql, String user, String password, String version) {
        Connection conn = null;
        try {
            conn = getConnection(url,user,password,version);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                Integer executeResult = stmt.executeUpdate(sql);
                LOGGER.info("url:" + url + ",sql:" + sql+",executeResult:"+executeResult);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Result转List @Title: resultSetToList @param rs @return @throws
     * java.sql.SQLException @throws
     */
    private List<Map<Object, Object>> resultSetToList(ResultSet rs) throws SQLException {
        if (rs == null) {
            return Collections.emptyList();
        }

        ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
        int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
        List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();

        while (rs.next()) {
            Map<Object, Object> rowData = new HashMap<Object, Object>();

            for (int i = 1; i <= columnCount; i++) {
                if (!rowData.containsKey(md.getColumnName(i))) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
            }

            list.add(rowData);
        }

        return list;
    }

    /**
     * 获取连接 @Title: getConnection @param url @return @throws Exception @throws
     */
    private Connection getConnection1(String url) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url);
        LOGGER.info("create a data connection");

        return conn;
    }


    private Connection getConnection8(String url) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url);
        LOGGER.info("create a data connection ------------version 8");

        return conn;
    }
    
    private Connection getConnection(String url,String uname ,String upass, String version) throws Exception {
        if (version.equals("8")){
            Class.forName("com.mysql.cj.jdbc.Driver");
        }else {
            Class.forName("com.mysql.jdbc.Driver");
        }
        Connection conn = DriverManager.getConnection(url,uname,upass);
        if (version.equals("8")){
            LOGGER.info("create a data connection ------------version 8");
        }else{
            LOGGER.info("create a data connection ------------version 5.7及以下");
        }

        return conn;
    }
    /**
     * 关闭连接 @Title: closeConnection @param conn @throws
     */
    private void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
            LOGGER.info("close a data connection");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
