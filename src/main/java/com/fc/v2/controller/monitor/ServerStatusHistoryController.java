package com.fc.v2.controller.monitor;

import com.fc.v2.common.base.BaseController;
import com.fc.v2.model.monitor.MonitorServer;
import com.fc.v2.model.monitor.MonitorServerExample;
import com.fc.v2.model.monitor.ServerStatusHistory;
import com.fc.v2.model.monitor.ServerStatusHistoryExample;
import com.fc.v2.util.DateUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ServerStatusHistoryController
 * @Author reisen
 * 参考： https://gitee.com/redtie/mycateye
 * @Description
 * @date 2024年11月06日
 **/

@RestController
@RequestMapping("/ServerStatusHistoryController")
public class ServerStatusHistoryController extends BaseController {

    /**
     *
     * @param monitorServerExample
     * @return
     */
    @PostMapping("/getListMonitorServer")
    private List<MonitorServer> getListMonitorServer(@RequestBody MonitorServerExample monitorServerExample){
        return monitorServerService.selectByExample(null);
    }

    /**
     *
     * @param serverId
     * @return
     */
    @PostMapping("/getListServerStatusHistory")
    private List<ServerStatusHistory> getListServerStatusHistory(Long serverId){
        ServerStatusHistoryExample serverStatusHistoryExample = new ServerStatusHistoryExample();
            //获取最新状态记录的ID
        ServerStatusHistoryExample.Criteria criteria = serverStatusHistoryExample.createCriteria();
        criteria.andServerIdEqualTo(serverId);
        criteria.andCreateTimeBetween(DateUtils.getNowStartDate(),DateUtils.getNowEndDate());
            serverStatusHistoryExample.setOrderByClause("update_date desc");
            PageHelper.startPage(0, 1);
        return serverStatusHistoryService.selectByExample(serverStatusHistoryExample);
    }

    /**
     *
     * @param lastId
     * @return
     */
    @GetMapping("/getOneServerStatusHistory")
    private ServerStatusHistory getOneServerStatusHistory(String lastId){
        return serverStatusHistoryService.selectByPrimaryKey(lastId);
    }

    /**
     *
     * @param serverStatusHistory
     * @return
     */
    @PostMapping("/insertServerStatusHistory")
    private ServerStatusHistory insertServerStatusHistory(@RequestBody ServerStatusHistory serverStatusHistory){
        int n = serverStatusHistoryService.insertSelective(serverStatusHistory);
        return serverStatusHistory;
    }

    /**
     *
     * @param serverStatusHistory
     * @return
     */
    @PostMapping("/updateServerStatusHistory")
    private int updateServerStatusHistory(@RequestBody ServerStatusHistory serverStatusHistory){
        return serverStatusHistoryService.updateByPrimaryKeySelective(serverStatusHistory);
    }
}
