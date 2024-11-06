package com.fc.v2.common.sharding;

import com.fc.v2.model.monitor.ServerStatusHistoryExample;
import com.fc.v2.service.monitor.ServerStatusHistoryService;
import com.fc.v2.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName ShardingTablesLoadRunner
 * @Author reisen
 * @Description
 * @date 2024年11月05日
 **/


@Order(value = 2) // 数字越小，越先执行
@Component
@Slf4j
public class ShardingTablesLoadRunner implements CommandLineRunner {


    @Autowired
    private ServerStatusHistoryService serverStatusHistoryService;

    private ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

    @Override
    public void run(String... args) {
//        ServerStatusHistoryExample example = new ServerStatusHistoryExample();
//        example.createCriteria().andCreateTimeEqualTo(new Date());
//        example.setOrderByClause(" create_time desc limit 1");
//        serverStatusHistoryService.selectByExample(example);
        ShardingAlgorithmTool.tableNameCacheReload();

    }

    @Scheduled(cron = "*/10 * * * *")
    public void Scheduled(){
        final Calendar c = Calendar.getInstance();
        //c.get(Calendar.DATE) 当前时间
        //c.getActualMaximum(Calendar.DATE) 本月最后一日
        if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {
            c.add(Calendar.DATE, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
            String monthStr = dateFormat.format(c.getTime());
            for (ShardingTableRuleConfiguration x : shardingRuleConfig.getTables()){
                String logicTableName = x.getLogicTable();
                log.info("配置的分表表名: " + logicTableName);
                ShardingAlgorithmTool.executeSql(Collections.singletonList("CREATE TABLE IF NOT EXISTS `" + logicTableName + '_' + monthStr + "` LIKE `" + logicTableName + "`;"));
            }

        }
    }




}
