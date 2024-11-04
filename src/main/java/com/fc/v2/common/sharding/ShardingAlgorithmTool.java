package com.fc.v2.common.sharding;

import com.alibaba.fastjson.JSONObject;
import com.fc.v2.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.core.env.Environment;

/**
 * @ClassName ShardingAlgorithmTool
 * @Author reisen
 * @Description
 * @date 2024年11月05日
 **/


@Slf4j
public class ShardingAlgorithmTool {

    private static final Set<String> tableNameCache = new CopyOnWriteArraySet<>();

    // 启动时，实际表中要有值，启动后，在ShardingTablesLoadRunner中先清空在缓存
    static  {
        //默认是当前月份的，最少有一个否则报错。
        String tableSuffix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String actualTableName = "mq_message".concat("_").concat(tableSuffix);
        tableNameCache.add(actualTableName);
    }

    @PostConstruct
    public void init() {

    }

    /**
     * 缓存重载方法
     */
    public static void tableNameCacheReload() {
        //读取数据库中所有表名
        List<String> tableList = getAllTableName();
        //清除缓存
        ShardingAlgorithmTool.tableNameCache.clear();
        //更新缓存
        ShardingAlgorithmTool.tableNameCache.addAll(tableList);
    }

    /**
     * 获取数据库中的表名
     */
    public static List<String> getAllTableName() {
        List<String> res = new ArrayList<>();
        // 获取数据中的表名
        Environment env = SpringUtil.getApplicationContext().getEnvironment();
        try (Connection connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));
             Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES  WHERE TABLE_SCHEMA='swell' AND TABLE_NAME like 'mq_message_2%'")) {
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
}