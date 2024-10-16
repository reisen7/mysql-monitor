package com.fc.v2.controller.mysql;

import com.fc.v2.service.mysql.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**   
 * @ClassName:  DashboardController   
 * @Description
 * @author: reisen
 * @date: 2024-10-16 18:20:36
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController
{
    @Autowired
    private DashboardService dashboardService;
    /**
     * 获取控制台概要信息
     * @Title: getDashboardOverview   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/dashboard-overview", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getDashboardOverview(@PathVariable Long serverId) {
        return dashboardService.getDashboardOverview(serverId);
    }
    
    /**
     * 获取控制台显示数据
     * @Title: getDashboardProcesslist   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/processlist", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getDashboardProcesslist(@PathVariable Long serverId) {
        return dashboardService.getDashboardProcesslist(serverId);
    }
}
