package com.fc.v2.common.sharding;

import com.alibaba.fastjson.JSONObject;
import com.fc.v2.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @ClassName ShardingAlgorithmTool
 * @Author reisen
 * @Description
 * @date 2024年11月05日
 **/


@Slf4j
public class ShardingAlgorithmTool {

    private static final Set<String> tableNameCache = new CopyOnWriteArraySet<>();

    /** 数据库配置 */
    private static final Environment ENV = SpringUtil.getApplicationContext().getEnvironment();

    private static final String DATASOURCE_URL = ENV.getProperty("my.sharding.create-table.url");

    private static final String DATASOURCE_USERNAME = ENV.getProperty("my.sharding.create-table.username");

    private static final String DATASOURCE_PASSWORD = ENV.getProperty("my.sharding.create-table.password");



    /** 表分片符号，例：t_user_202201 中，分片符号为 "_" */
    private static final String TABLE_SPLIT_SYMBOL = "_";

    /**
     * 缓存重载方法
     */
    public static void tableNameCacheReload() {
        log.info(">>>>>>>>>> 【INFO】刷新分表缓存 <<<<<<<<<<" );
        //读取数据库中所有表名
        List<String> tableList = getAllTableName();
        //清除缓存
        ShardingAlgorithmTool.tableNameCache.clear();
        //更新缓存
        ShardingAlgorithmTool.tableNameCache.addAll(tableList);
        log.info(">>>>>>>>>> 【INFO】缓存中的表:{} <<<<<<<<<<",tableList);
    }

    /**
     * 获取数据库中的表名
     */
    public static List<String> getAllTableName() {
        List<String> res = new ArrayList<>();
        // 获取数据中的表名
        try (Connection connection = DriverManager.getConnection(DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);
             Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES  WHERE TABLE_SCHEMA='mysql-monitor' AND TABLE_NAME like 'server_status_history_%'")) {
                while (rs.next()) {
                    res.add(rs.getString(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取缓存中的表名
     * @return
     */
    public static Set<String> cacheTableNames() {
        return ShardingAlgorithmTool.tableNameCache;
    }


    /**
     * 执行SQL
     * @param sqlList SQL集合
     */

    public static void executeSql(List<String> sqlList) {
        if (StringUtils.isEmpty(DATASOURCE_URL) || StringUtils.isEmpty(DATASOURCE_USERNAME) || StringUtils.isEmpty(DATASOURCE_PASSWORD)) {
            log.error(">>>>>>>>>> 【ERROR】数据库连接配置有误，请稍后重试，url:{}, username:{}, password:{}", DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);
            throw new IllegalArgumentException("数据库连接配置有误，请稍后重试");
        }
        try (Connection conn = DriverManager.getConnection(DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD)) {
            try (Statement st = conn.createStatement()) {
                conn.setAutoCommit(false);
                for (String sql : sqlList) {
                    st.execute(sql);
                }
            } catch (Exception e) {
                conn.rollback();
                log.error(">>>>>>>>>> 【ERROR】数据表创建执行失败，请稍后重试，原因：{}", e.getMessage(), e);
                throw new IllegalArgumentException("数据表创建执行失败，请稍后重试");
            }
        } catch (SQLException e) {
            log.error(">>>>>>>>>> 【ERROR】数据库连接失败，请稍后重试，原因：{}", e.getMessage(), e);
            throw new IllegalArgumentException("数据库连接失败，请稍后重试");
        }
    }

    /**
     * 创建分表2
     * @param logicTableName  逻辑表
     * @param resultTableName 真实表名，例：t_user_202201
     * @return 创建结果（true创建成功，false未创建）
     */
    public static boolean createShardingTable(String logicTableName, String resultTableName) {
        // 根据日期判断，当前月份之后分表不提前创建
        String month = resultTableName.replace(logicTableName + TABLE_SPLIT_SYMBOL,"");
        YearMonth shardingMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyyMM"));

        log.info(">>>>>>>>>> 【INFO】正在创建分表：{}",resultTableName);
        synchronized (logicTableName.intern()) {
            // 缓存中无此表，则建表并添加缓存
            executeSql(Collections.singletonList("CREATE TABLE IF NOT EXISTS `" + resultTableName + "` LIKE `" + logicTableName + "`;"));
        }
        return true;
    }



    /**
     * 获取所有表名
     * @return 表名集合
     * @param logicTableName 逻辑表
     */
    public static List<String> getAllTableNameBySchema(String logicTableName) {
        List<String> tableNames = new ArrayList<>();
        if (StringUtils.isEmpty(DATASOURCE_URL) || StringUtils.isEmpty(DATASOURCE_USERNAME) || StringUtils.isEmpty(DATASOURCE_PASSWORD)) {
            log.error(">>>>>>>>>> 【ERROR】数据库连接配置有误，请稍后重试，url:{}, username:{}, password:{}", DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);
            throw new IllegalArgumentException("数据库连接配置有误，请稍后重试");
        }
        try (Connection conn = DriverManager.getConnection(DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);
             Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery("show TABLES like '" + logicTableName + TABLE_SPLIT_SYMBOL + "%'")) {
                while (rs.next()) {
                    String tableName = rs.getString(1);
                    // 匹配分表格式 例：^(t\_contract_\d{6})$
                    if (tableName != null && tableName.matches(String.format("^(%s\\d{6})$", logicTableName + TABLE_SPLIT_SYMBOL))) {
                        tableNames.add(rs.getString(1));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(">>>>>>>>>> 【ERROR】数据库连接失败，请稍后重试，原因：{}", e.getMessage(), e);
            throw new IllegalArgumentException("数据库连接失败，请稍后重试");
        }
        return tableNames;
    }


}