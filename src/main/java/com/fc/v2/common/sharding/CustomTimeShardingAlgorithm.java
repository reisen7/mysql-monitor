package com.fc.v2.common.sharding;
import com.fc.v2.util.SpringUtil;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.springframework.core.env.Environment;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @ClassName CustomTimeShardingAlgorithm
 * @Author reisen
 * @Description
 * @date 2024年11月05日
 **/

@Slf4j
public class CustomTimeShardingAlgorithm implements StandardShardingAlgorithm<Date> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 精准分片
     * @param collection 对应分片库中所有分片表的集合
     * @param preciseShardingValue
     * @return 表名
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        log.info("进入doSharding返回字符串");
        Object value = preciseShardingValue.getValue();
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
        if(!collection.contains(actualTableName)){
            collection.add(actualTableName);
        }
        return actualTableName;
    }

    /**
     * 范围分片
     * @param collection 对应分片库中所有分片表的集合
     * @param rangeShardingValue 分片范围
     * @return 表名集合
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Date> rangeShardingValue) {
        log.info("进入doSharding返回列表");
        // 逻辑表名
        String logicTableName = rangeShardingValue.getLogicTableName();
        // 范围参数
        Range<Date> valueRange = rangeShardingValue.getValueRange();
        //起始时间  结束时间
        LocalDateTime start = null;
        LocalDateTime end = null;
        Object lowerEndpoint = (Object)valueRange.lowerEndpoint();
        Object upperEndpoint = (Object)valueRange.upperEndpoint();
        log.info("lowerEndpoint ======"+lowerEndpoint.toString());
        log.info("upperEndpoint ======"+upperEndpoint.toString());
        if(lowerEndpoint instanceof  String){
            String lower = (String) lowerEndpoint;
            String upper = (String) upperEndpoint;
            start = LocalDateTime.parse(lower,formatter);
            end = LocalDateTime.parse(upper,formatter);
        }else{
            start = valueRange.lowerEndpoint().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            end = valueRange.upperEndpoint().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            log.info("start ======",start.format(DateTimeFormatter.ofPattern("yyyyMM")));
            log.info("end ======"+end.format(DateTimeFormatter.ofPattern("yyyyMM")));
        }
        if(end.isAfter(LocalDateTime.now())){
            end = LocalDateTime.now();
        }
        // 查询范围的表
        Set<String> queryRangeTables = extracted(logicTableName, start, end);
        return queryRangeTables;
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
}