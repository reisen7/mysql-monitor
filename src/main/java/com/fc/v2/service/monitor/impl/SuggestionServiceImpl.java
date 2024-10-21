package com.fc.v2.service.monitor.impl;

import com.fc.v2.dto.QueryResult;
import com.fc.v2.dto.RestResponse;
import com.fc.v2.dto.Suggestion;
import com.fc.v2.mapper.mysql.MonitorServerMapper;
import com.fc.v2.model.monitor.MonitorServer;
import com.fc.v2.service.monitor.JdbcService;
import com.fc.v2.service.monitor.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SuggestionServiceImpl
 * @Description
 * @author: reisen
 * @date: 2024-10-16
 * 
 */
@Service
public class SuggestionServiceImpl implements SuggestionService
{
    @Autowired
    private MonitorServerMapper monitorServerMapper;
    
    @Autowired
    protected JdbcService jdbcService;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.bqjr.dbeye.agent.service.SuggestionService#getSuggestion(java.lang.
     * Long)
     */
    @Override
    public Object getSuggestion(Long serverId)
    {
        RestResponse<List<Suggestion>> restResponse = new RestResponse<List<Suggestion>>();
        List<Suggestion> suggestions = new ArrayList<Suggestion>();
        // 获取目标MySQL节点信息
        MonitorServer monitorServer = monitorServerMapper.selectByPrimaryKey(serverId);
        if (monitorServer == null)
        {
            restResponse.setMessage("monitorServer is null");
            restResponse.setCode(1);
            return restResponse;
        }
        // 获取慢查询数量占比建议
        Suggestion suggestionSlowQueries = getSuggestionBySlowQueries(monitorServer);
        suggestions.add(suggestionSlowQueries);
        restResponse.setCode(0);
        restResponse.setMessage("success");
        restResponse.setData(suggestions);
        return restResponse;
    }
    
    /**
     * 获取慢查询数量占比建议 @Title: getSuggestionBySlowQueries @param
     * monitorServer @return @throws
     */
    private Suggestion getSuggestionBySlowQueries(MonitorServer monitorServer)
    {
        QueryResult<List<Map<Object, Object>>> queryResult = getAllStatus(monitorServer);
        List<Map<Object, Object>> statusList = queryResult.getData();
        String slowQueries = getStatusValueByVariableName(statusList, "Slow_queries");
        String questions = getStatusValueByVariableName(statusList, "Questions");
        long value = new BigDecimal(slowQueries).divide(new BigDecimal(questions), 2, BigDecimal.ROUND_HALF_UP)
            .multiply(new BigDecimal("100"))
            .longValue();
        Suggestion suggestion = new Suggestion();
        suggestion.setProblem("value:" + value);
        return suggestion;
    }
    
    /**
     * 获取当前状态的所有指标 @Title: getAllStatus @param monitorServer @return @throws
     */
    private QueryResult<List<Map<Object, Object>>> getAllStatus(MonitorServer monitorServer)
    {
        String host = monitorServer.getHost();
        String port = String.valueOf(monitorServer.getPort());
        String username = monitorServer.getUsername();
        String password = monitorServer.getPassword();
        String version = monitorServer.getVersion();
        String sql = "show status";
        return getQueryResult(host, port, sql, username, password, version);
    }
    
    /**
     * 获取查询结果集 @Title: getQueryResult @param host @param port @param sql @param
     * username @param password @return @throws
     */
    private QueryResult<List<Map<Object, Object>>> getQueryResult(String host, String port, String sql, String username,
        String password, String version)
    {
        String dataSourceUrl = "jdbc:mysql://" + host + ":" + port + "/" + "?useSSL=false" +((version.equals("8")) ?"&serverTimezone=GMT":"");;
        QueryResult<List<Map<Object, Object>>> queryResult =
            jdbcService.queryForList(dataSourceUrl, sql, username, password, version);
        
        return queryResult;
    }
    
    /**
     * 根据变量名获取变量值 @Title: getStatusValueByVariableName @param statusList @param
     * variableName @return @throws
     */
    private String getStatusValueByVariableName(List<Map<Object, Object>> statusList, String variableName)
    {
        String value = "";
        for (Map<Object, Object> map : statusList)
        {
            String vName = (String)map.get("Variable_name");
            if (vName.equals(variableName))
            {
                value = (String)map.get("Value");
                break;
            }
        }
        return value;
    }
}
