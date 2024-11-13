package com.fc.v2.common.sharding;
import com.fc.v2.util.SpringUtil;
import com.google.common.collect.Range;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName CustomTimeShardingAlgorithm
 * @Author reisen
 * @Description
 * @date 2024年11月05日
 **/

@Slf4j
public class CustomTimeShardingAlgorithm implements StandardShardingAlgorithm<Date> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Getter
    private int autoTablesAmount;


    /**
     * 精准分片
     * @param collection 对应分片库中所有分片表的集合
     * @param preciseShardingValue
     * @return 表名
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        log.info(">>>>>>>>>> 【INFO】精确分片，节点配置表名：{}" ,collection);
        Object value = preciseShardingValue.getValue();
//        log.info(">>>>>>>>>> 【INFO】分片键值：{}", value);
        String tableSuffix = null;
        if(value instanceof Date){
            LocalDate localDate = ((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            tableSuffix = localDate.format(DateTimeFormatter.ofPattern("yyyyMM"));
        }else{
            String column = (String)value;
            tableSuffix = LocalDateTime.parse(column, formatter).format(DateTimeFormatter.ofPattern("yyyyMM"));
        }

        String logicTableName = preciseShardingValue.getLogicTableName();
        String actualTableName = logicTableName.concat("_").concat(tableSuffix);

        // 检查是否需要初始化
        if (collection.size() == 1) {
            if (collection.contains(logicTableName)){
                collection.add(actualTableName);
                return getShardingTableAndCreate(logicTableName,actualTableName,collection);
            }
            // 如果只有一个表，说明需要获取所有表名
            List<String> allTableNameBySchema = ShardingAlgorithmTool.getAllTableNameBySchema(logicTableName);
            collection.clear();
            collection.addAll(allTableNameBySchema);
            autoTablesAmount = allTableNameBySchema.size();
            return actualTableName;
        }

        if(!collection.contains(actualTableName)){
            collection.add(actualTableName);
        }
        return getShardingTableAndCreate(logicTableName,actualTableName,collection);
    }

    /**
     * 范围分片
     * @param collection 对应分片库中所有分片表的集合
     * @param rangeShardingValue 分片范围
     * @return 表名集合
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Date> rangeShardingValue) {
        log.info(">>>>>>>>>> 【INFO】范围分片，节点配置表名：{}" ,collection);
        // 逻辑表名
        String logicTableName = rangeShardingValue.getLogicTableName();
        // 范围参数
        Range<Date> valueRange = rangeShardingValue.getValueRange();
        //起始时间  结束时间
        LocalDateTime start = null;
        LocalDateTime end = null;
        Object lowerEndpoint = (Object)valueRange.lowerEndpoint();
        Object upperEndpoint = (Object)valueRange.upperEndpoint();
        if(lowerEndpoint instanceof  String){
            String lower = (String) lowerEndpoint;
            String upper = (String) upperEndpoint;
            start = LocalDateTime.parse(lower,formatter);
            end = LocalDateTime.parse(upper,formatter);
        }else{
            start = valueRange.lowerEndpoint().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            end = valueRange.upperEndpoint().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            log.info(">>>>>>>>>> start ====== {}",start.format(DateTimeFormatter.ofPattern("yyyyMM")));
            log.info(">>>>>>>>>> end ====== {}",end.format(DateTimeFormatter.ofPattern("yyyyMM")));
        }
        if(end.isAfter(LocalDateTime.now())){
            end = LocalDateTime.now();
        }
        // 检查是否需要初始化
        if (collection.size() == 1){
            return Collections.singleton(getTableNameByDate(start,logicTableName));
        }
        // 查询范围的表
        Set<String> queryRangeTables = extracted(logicTableName, start, end);
        return getShardingTablesAndCreate(logicTableName,queryRangeTables,collection);
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public Properties getProps() {
        return null;
    }


    /**
     * 根据范围计算表名
     * @param logicTableName 表名前缀
     * @param lowerEndpoint 起点
     * @param upperEndpoint 终点
     * @return 最终的表名集合
     */
    private Set<String> extracted(String logicTableName, LocalDateTime lowerEndpoint, LocalDateTime upperEndpoint) {
        Set<String> rangeTable = new HashSet<>();
        while (lowerEndpoint.isBefore(upperEndpoint)) {
            String str = getTableNameByDate(lowerEndpoint, logicTableName);
            rangeTable.add(str);
            lowerEndpoint = lowerEndpoint.plusMonths(1);
        }
        // 获取物理表名
        String tableName = getTableNameByDate(upperEndpoint, logicTableName);
        rangeTable.add(tableName);
        return rangeTable;
    }
    /**
     * 根据日期获取表名
     * @param dateTime 日期
     * @param logicTableName 逻辑表名
     * @return 物理表名
     */
    private String getTableNameByDate(LocalDateTime dateTime, String logicTableName) {
        String tableSuffix = dateTime.format(DateTimeFormatter.ofPattern("yyyyMM"));
        return logicTableName.concat("_").concat(tableSuffix);
    }

    @Override
    public void init(Properties properties) {

    }


    /**
     * 检查分表获取的表名是否存在，不存在则自动建表
     *
     * @param logicTableName        逻辑表
     * @param resultTableNames     真实表名，例：t_user_202201
     * @param availableTargetNames 可用的数据库表名
     * @return 存在于数据库中的真实表名集合
     */
    public Set<String> getShardingTablesAndCreate(String logicTableName, Collection<String> resultTableNames, Collection<String> availableTargetNames) {
        return resultTableNames.stream().map(o -> getShardingTableAndCreate(logicTableName, o, availableTargetNames)).collect(Collectors.toSet());
    }


    /**
     * 检查分表获取的表名是否存在，不存在则自动建表
     * @param logicTableName   逻辑表
     * @param resultTableName 真实表名，例：t_user_202201
     * @return 确认存在于数据库中的真实表名
     */
    private String getShardingTableAndCreate(String logicTableName, String resultTableName, Collection<String> availableTargetNames) {
        // 缓存中有此表则返回，没有则判断创建
        if (availableTargetNames.contains(resultTableName)) {
            return resultTableName;
        } else {
            // 检查分表获取的表名不存在，需要自动建表
            boolean isSuccess = ShardingAlgorithmTool.createShardingTable(logicTableName, resultTableName);
            if (isSuccess) {
                // 如果建表成功，需要更新缓存
                availableTargetNames.add(resultTableName);
                autoTablesAmount++;
                return resultTableName;
            } else {
                // 如果建表失败，返回逻辑空表
                return logicTableName;
            }
        }
    }

}