package com.fc.v2.service.mysql;

import com.fc.v2.dto.PagedDto;
import com.fc.v2.dto.Processlist;

/**   
 * @ClassName:  DashboardService   
 * @Description
 * @author: reisen
 * @date:   2024-10-16 18:20:36
 * */
public interface DashboardService
{
    /**
     * 获取控制台概要信息
     * @Title: getDashboardOverview   
     * @param serverId
     * @return        
     * @throws
     */
    public Object getDashboardOverview(Long serverId);
    /**
     * 获取控制台显示数据
     * @Title: getDashboardProcesslist   
     * @param serverId
     * @return        
     * @throws
     */
    public PagedDto<Processlist> getDashboardProcesslist(Long serverId);
}
