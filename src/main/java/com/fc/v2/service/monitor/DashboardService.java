package com.fc.v2.service.monitor;

import com.fc.v2.common.domain.AjaxResult;

/**
 * @ClassName DashboardService
 * @Author reisen
 * @Description
 * @date 2024年10月17日
 **/
public interface DashboardService {

    /**
     * 获取控制台概要信息
     * @Title: getDashboardOverview
     * @param serverId
     * @return
     * @throws
     */
    public AjaxResult getDashboardOverview(Long serverId);
    /**
     * 获取控制台显示数据
     * @Title: getDashboardProcesslist
     * @param serverId
     * @return
     * @throws
     */
    public AjaxResult getDashboardProcesslist(Long serverId);

}
