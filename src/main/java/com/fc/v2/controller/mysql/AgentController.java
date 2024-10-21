package com.fc.v2.controller.mysql;

import com.fc.v2.service.monitor.StatementService;
import com.fc.v2.service.monitor.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 监控Controller
 * @ClassName MonClusterController
 * @author reisen
 * @Description
 * @date 2024-08-16 18:20:36
 */
@RestController
public class AgentController
{
    @Autowired
    private StatementService statementService;

    @Autowired
    private SuggestionService suggestionService;
    
    /**
     * 查看执行计划
     * @Title: getExplain   
     * @param request
     * @return        
     * @throws
     */
    @RequestMapping(value = "/get/explain", method = {RequestMethod.POST, RequestMethod.GET})
    public Object getExplain(HttpServletRequest request)
    {
        Long serverId = new Long(request.getParameter("serverId").toString());
        String schema = request.getParameter("schema");
        String sql = request.getParameter("sql");
        
        return statementService.getExplain(serverId, schema, sql);
    }
    
    /**
     * 根据指定MySQL节点的状态，获取优化建议
     * @Title: getSuggestion   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/suggestion/{serverId}", method = {RequestMethod.POST, RequestMethod.GET})
    public Object getSuggestion(@PathVariable Long serverId)
    {
        return suggestionService.getSuggestion(serverId);
    }
    
}
