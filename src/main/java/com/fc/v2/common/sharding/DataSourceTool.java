package com.fc.v2.common.sharding;

import com.fc.v2.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @ClassName DataSourceTool
 * @Author reisen
 * @Description
 * @date 2024年11月05日
 **/
@Slf4j
public class DataSourceTool {

    /** 数据库配置 */
    private static final Environment ENV = SpringUtil.getBean(Environment.class);
    private static final String DATASOURCE_URL = ENV.getProperty("my.sharding.create-table.url");
    private static final String DATASOURCE_USERNAME = ENV.getProperty("my.sharding.create-table.username");
    private static final String DATASOURCE_PASSWORD = ENV.getProperty("my.sharding.create-table.password");


    /**
     * 执行SQL
     * @param sqlList SQL集合
     */

    public static void executeSql(List<String> sqlList) {
        if (StringUtils.isEmpty(DATASOURCE_URL) || StringUtils.isEmpty(DATASOURCE_USERNAME) || StringUtils.isEmpty(DATASOURCE_PASSWORD)) {
            log.error(">>>>>>>>>> 【ERROR】数据库连接配置有误，请稍后重试，URL:{}, username:{}, password:{}", DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);
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

}
