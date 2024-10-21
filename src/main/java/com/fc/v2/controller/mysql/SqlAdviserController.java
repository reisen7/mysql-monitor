package com.fc.v2.controller.mysql;

import com.fc.v2.service.monitor.SqlAdviserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**   
 * @ClassName:  SqlAdviserController   
 * @Description
 * @author: reisen
 * @date:   2024-10-16 18:20:36
 *     
 */
@RestController
@RequestMapping("/sql-adviser")
public class SqlAdviserController
{
    @Autowired
    private SqlAdviserService sqlAdviserService;
    
    /**
     * 获取SQL建议
     * @Title: getAdvice   
     * @param request
     * @return        
     * @throws
     */
    @RequestMapping(value = "/advice", method = {RequestMethod.POST, RequestMethod.GET})
    public Object getAdvice(HttpServletRequest request)
    {
        Long serverId = new Long(request.getParameter("serverId").toString());
        String schema = request.getParameter("schema");
        String sql = request.getParameter("sql");
        return sqlAdviserService.getAdvice(serverId, schema, sql);
    }
}
