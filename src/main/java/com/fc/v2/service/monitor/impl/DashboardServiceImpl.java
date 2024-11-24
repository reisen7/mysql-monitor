package com.fc.v2.service.monitor.impl;

import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.dto.*;
import com.fc.v2.mapper.mysql.ServerStatusHistoryMapper;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.model.monitor.ServerStatusHistory;
import com.fc.v2.model.monitor.ServerStatusHistoryExample;
import com.fc.v2.model.mysql.Constant;
import com.fc.v2.service.monitor.DashboardService;
import com.fc.v2.util.MiscUtil;
import com.fc.v2.util.SysSampleUtil;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**   
 * @ClassName:  DashboardServiceImpl   
 * @Description
 * @author: reisen
 * @date:  2024-10-16
 **/
@Service
@Transactional
public class DashboardServiceImpl extends AbstractService implements DashboardService
{

    @Autowired
    private ServerStatusHistoryMapper serverStatusHistoryMapper;
    
    /* (non-Javadoc)
     * @see io.mycat.eye.agent.service.DashboardService#getDashboardOverview(java.lang.Long)
     */
    @Override
    public AjaxResult getDashboardOverview(Long serverId)
    {
        DashboardOverview dashboardOverview = getUptimeThreadsInnodbBytes(serverId);
        dashboardOverview = getSchemaTotalDataTotal(dashboardOverview, serverId);
        dashboardOverview = getTableTotal(dashboardOverview, serverId);
        dashboardOverview = getUserTotal(dashboardOverview, serverId);
        dashboardOverview = getQpsChatData(dashboardOverview, serverId);
        dashboardOverview = getQueryChatData(dashboardOverview, serverId);

        if (dashboardOverview == null ){
            return AjaxResult.error("查询数据为空");
        }
        return AjaxResult.successData(200,dashboardOverview);
    }

    @Override
    public AjaxResult getDashboardChart(Long serverId) {
        DashboardOverview dashboardOverview = new DashboardOverview();
        dashboardOverview = getQpsChatData(dashboardOverview, serverId);
//        dashboardOverview = getQueryChatData(dashboardOverview, serverId);

        if (dashboardOverview == null ){
            return AjaxResult.error("查询数据为空");
        }
        return AjaxResult.successData(200,dashboardOverview);
    }

    /**
     * 获取查询统计图表信息
     * @Title: getQueryChatData   
     * @param dashboardOverview
     * @param serverId
     * @return        
     * @throws
     */
    private DashboardOverview getQueryChatData(DashboardOverview dashboardOverview, Long serverId)
    {
        String sql =
            "select sum(total) as sum_total,statement from sys.user_summary_by_statement_type t GROUP BY t.statement";
        QueryResult<List<Map<Object, Object>>> queryResult = getQueryResult(serverId, sql);
        if (queryResult.isSuccess())
        {
            List<Map<Object, Object>> resultList = queryResult.getData();
            String[] xAxisData = new String[resultList.size()];
            String[] seriesData = new String[resultList.size()];
            for (int i = 0; i < resultList.size(); i++)
            {
                xAxisData[i] = resultList.get(i).get("statement").toString();
                seriesData[i] = resultList.get(i).get("sum_total").toString();
            }
            ChartDataDto chartDataDto=new ChartDataDto();
            chartDataDto.setxAxisData(xAxisData);
            chartDataDto.setSeriesData(seriesData);
            dashboardOverview.setQueryChartData(chartDataDto);
        }
        else
        {
            logger.error(queryResult.getException());
        }
        return dashboardOverview;
    }
    
    /**
     * 获取30分钟内的QPS图表数据
     * @Title: getQpsChatData   
     * @param dashboardOverview
     * @param serverId
     * @return        
     * @throws
     */
    private DashboardOverview getQpsChatData(DashboardOverview dashboardOverview, Long serverId)
    {
        ServerStatusHistoryExample example = new ServerStatusHistoryExample();
        example.setOrderByClause("create_time");
        ServerStatusHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andServerIdEqualTo(serverId);
        //获取30分钟的数据
        criteria.andCreateTimeBetween(org.apache.commons.lang3.time.DateUtils.addMinutes(new Date(), -180), new Date());
        List<ServerStatusHistory> selectByExample = serverStatusHistoryMapper.selectByExample(example);
        //获取系统采样样本
        List<ServerStatusHistory> sampleList = SysSampleUtil.getSysSample(selectByExample, Constant.SAMPLE_COUNT);
        List<String> xDataList = new ArrayList<>();
        //qps
        List<String> qpsDataList = new ArrayList<>();
        for (ServerStatusHistory mysqlStatusHistory : sampleList)
        {
            xDataList.add(MiscUtil.getFormatDateTime(mysqlStatusHistory.getCreateTime()));
            qpsDataList.add(mysqlStatusHistory.getQuestionsPersecond());
        }
        String[] xAxisData = new String[xDataList.size()];
        String[] seriesData = new String[xDataList.size()];
        xDataList.toArray(xAxisData);
        qpsDataList.toArray(seriesData);
        ChartDataDto chartDataDto = new ChartDataDto();
        chartDataDto.setxAxisData(xAxisData);
        chartDataDto.setSeriesData(seriesData);
        dashboardOverview.setQpsChartData(chartDataDto);
        return dashboardOverview;
    }
    
    /**
     * 获取user总数
     * @Title: getUserTotal   
     * @param dashboardOverview
     * @param serverId
     * @return        
     * @throws
     */
    private DashboardOverview getUserTotal(DashboardOverview dashboardOverview, Long serverId)
    {
        String sql = "select count(DISTINCT user) as user_total from mysql.user";
        QueryResult<List<Map<Object, Object>>> queryResult = getQueryResult(serverId, sql);
        if (queryResult.isSuccess())
        {
            List<Map<Object, Object>> resultList = queryResult.getData();
            dashboardOverview.setUserTotal(resultList.get(0).get("user_total").toString());
        }
        else
        {
            logger.error(queryResult.getException());
        }
        return dashboardOverview;
    }
    
    /**
     * 获取table总数
     * @Title: getTableTotal   
     * @param dashboardOverview
     * @param serverId
     * @return        
     * @throws
     */
    private DashboardOverview getTableTotal(DashboardOverview dashboardOverview, Long serverId)
    {
        String sql = "SELECT count(DISTINCT TABLE_NAME) as table_total FROM information_schema.TABLES";
        QueryResult<List<Map<Object, Object>>> queryResult = getQueryResult(serverId, sql);
        if (queryResult.isSuccess())
        {
            List<Map<Object, Object>> resultList = queryResult.getData();
            dashboardOverview.setTableTotal(resultList.get(0).get("table_total").toString());
        }
        else
        {
            logger.error(queryResult.getException());
        }
        return dashboardOverview;
    }
    
    /**
     * 查询schema总数/数据总量
     * @Title: getSchemaTotal   
     * @param dashboardOverview
     * @param serverId
     * @return        
     * @throws
     */
    private DashboardOverview getSchemaTotalDataTotal(DashboardOverview dashboardOverview, Long serverId)
    {
        String sql =
            "SELECT round((sum(DATA_LENGTH)+sum(INDEX_LENGTH))/1024/1024,0) as size,TABLE_SCHEMA FROM information_schema.TABLES group by TABLE_SCHEMA";
        QueryResult<List<Map<Object, Object>>> queryResult = getQueryResult(serverId, sql);
        if (queryResult.isSuccess())
        {
            List<Map<Object, Object>> resultList = queryResult.getData();
            int schemaTotal = resultList.size();
            dashboardOverview.setSchemaTotal(String.valueOf(schemaTotal));
            Float dataTotal = 0F;
            for (Map<Object, Object> map : resultList)
            {
                dataTotal += new Float(map.get("size").toString());
            }
            dashboardOverview.setDataTotal(dataTotal.toString() + "MB");
        }
        else
        {
            logger.error(queryResult.getException());
        }
        return dashboardOverview;
    }
    
    private DashboardOverview getUptimeThreadsInnodbBytes(Long serverId)
    {
        String sql =
            "show global status where VARIABLE_NAME in('uptime','Threads_running','Innodb_buffer_pool_pages_free','Innodb_buffer_pool_pages_total','Bytes_received','Bytes_sent')";
        QueryResult<List<Map<Object, Object>>> queryResult = getQueryResult(serverId, sql);
        DashboardOverview dashboardOverview=null;
        if (queryResult.isSuccess())
        {
            dashboardOverview = new DashboardOverview();
            List<Map<Object, Object>> resultList = queryResult.getData();
            Long innodbBufferPoolPagesFree = 0L;
            Long innodbBufferPoolPagesTotal = 0L;
			String Variable_name="VARIABLE_NAME";//"Variable_name";
			String Value="VARIABLE_VALUE";//Value;			
			String Variable=(String)resultList.get(0).get(Variable_name);
			if (Variable==null){
				Variable_name="Variable_name";
				Value="Value";				
			}
            for (Map<Object, Object> map : resultList)
            {
                String variableName = (String)map.get(Variable_name);
                if (variableName.equals("Uptime"))
                {
                    dashboardOverview.setUptime(MiscUtil.getHumanTimeBySeconds(new Long(map.get(Value).toString())));
                }
                if (variableName.equals("Bytes_received"))
                {
                    dashboardOverview
                        .setBytesReceived(MiscUtil.getHumanSizeByBytes(new Long(map.get(Value).toString())));
                }
                if (variableName.equals("Bytes_sent"))
                {
                    dashboardOverview.setBytesSent(MiscUtil.getHumanSizeByBytes(new Long(map.get(Value).toString())));
                }
                if (variableName.equals("Innodb_buffer_pool_pages_free"))
                {
                    innodbBufferPoolPagesFree = new Long(map.get(Value).toString());
                }
                if (variableName.equals("Innodb_buffer_pool_pages_total"))
                {
                    innodbBufferPoolPagesTotal = new Long(map.get(Value).toString());
                }
                if (variableName.equals("Threads_running"))
                {
                    dashboardOverview.setThreadsRunning((String)map.get(Value));
                }
            }
            dashboardOverview.setInnoDBBufferPoolSize(MiscUtil.getPercentage((innodbBufferPoolPagesTotal - innodbBufferPoolPagesFree), innodbBufferPoolPagesTotal));
        }
        else
        {
            logger.error(queryResult.getException());
        }
        return dashboardOverview;
    }

    /* (non-Javadoc)
     * @see io.mycat.eye.agent.service.DashboardService#getDashboardProcesslist(java.lang.Long)
     */
    @Override
    public PageInfo<Processlist> getDashboardProcesslist(Processlist process,Tablepar tablepar,Long serverId)
    {

        String sql="show processlist";
        QueryResult<List<Map<Object, Object>>> queryResult = getQueryResult(serverId, sql);
        if (queryResult.isSuccess())
        {
            List<Map<Object,Object>> resultListOriginal = queryResult.getData();
            // 倒序
            Collections.reverse(resultListOriginal);
            // 搜索
            List<Map<Object,Object>> resultList;
            if (!process.equals(null)) {
                resultList = resultListOriginal.stream().filter(x->{
                    int flag = 0;
                    if (x.get("Command")!=null&&!x.get("Command").equals("")&&(x.get("Command").toString()).equals(process.getCommand())) {
                        flag ++;
                    }
                    if (x.get("db")!=null&&!x.get("db").equals("")&&(x.get("db").toString()).equals(process.getDb())) {
                        flag ++;
                    }
                    if (x.get("Host")!=null&&!x.get("Host").equals("")&&(x.get("Host").toString()).equals(process.getHost())) {
                        flag ++;
                    }
                    if (x.get("Info")!=null&&!x.get("Info").equals("")&&(x.get("Info").toString()).equals(process.getInfo())) {
                        flag ++;
                    }
                    if (x.get("Id")!=null&&!x.get("Id").equals("")&&(x.get("Id").toString()).equals(process.getId())) {
                        flag ++;
                    }
                    if (x.get("State")!=null&&!x.get("State").equals("")&&(x.get("State").toString()).equals(process.getState())) {
                        flag ++;
                    }
                    if (x.get("Time")!=null&&!x.get("Time").equals("")&&(x.get("Time").toString()).equals(process.getTime())) {
                        flag ++;
                    }
                    if (x.get("User")!=null&&!x.get("User").equals("")&&(x.get("User").toString()).equals(process.getUser())) {
                        flag ++;
                    }
                    return flag > 0;
                }

                ).collect(Collectors.toList());
            }else{
                resultList = resultListOriginal;
            }


            List<Processlist> processlists=new ArrayList<>();
            for (int i = (tablepar.getPage()-1)*tablepar.getLimit(); i < ((tablepar.getPage()+1)*tablepar.getLimit() > resultList.size() ? resultList.size() : (tablepar.getPage()+1)*tablepar.getLimit()); i++)
            {     
                Processlist processlist=new Processlist();
                processlist.setCommand(resultList.get(i).get("Command")==null?"":resultList.get(i).get("Command").toString());
                processlist.setDb(resultList.get(i).get("db")==null?"":resultList.get(i).get("db").toString());
                processlist.setHost(resultList.get(i).get("Host")==null?"":resultList.get(i).get("Host").toString());
                processlist.setId(resultList.get(i).get("Id")==null?"":resultList.get(i).get("Id").toString());
                processlist.setInfo(resultList.get(i).get("Info")==null?"":resultList.get(i).get("Info").toString());
                processlist.setState(resultList.get(i).get("State")==null?"":resultList.get(i).get("State").toString());
                processlist.setTime(resultList.get(i).get("Time")==null?"":resultList.get(i).get("Time").toString());
                processlist.setUser(resultList.get(i).get("User")==null?"":resultList.get(i).get("User").toString());
                processlists.add(processlist);
            }
            PageInfo<Processlist> pageInfo = new PageInfo<Processlist>(processlists);
            pageInfo.setTotal(resultList.size());
            return pageInfo;
        }
        else
        {
            logger.error(queryResult.getException());
        }
        return new PageInfo<>(null);
    }
    
}
