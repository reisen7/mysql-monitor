package com.fc.v2.controller.monitor;

import com.fc.v2.common.base.BaseController;
import com.fc.v2.model.monitor.MonitorServer;
import com.fc.v2.model.monitor.MonitorServerExample;
import com.fc.v2.model.monitor.ServerStatusHistory;
import com.fc.v2.model.monitor.ServerStatusHistoryExample;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ServerStatusHistoryController
 * @Author reisen
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
    private List<MonitorServer> getListMonitorServer(MonitorServerExample monitorServerExample){
        return monitorServerService.selectByExample(monitorServerExample);
    }

    /**
     *
     * @param serverStatusHistoryExample
     * @return
     */
    @PostMapping("/getListServerStatusHistory")
    private List<ServerStatusHistory> getListServerStatusHistory(ServerStatusHistoryExample serverStatusHistoryExample){
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
    private ServerStatusHistory insertServerStatusHistory(ServerStatusHistory serverStatusHistory){
        int n = serverStatusHistoryService.insertSelective(serverStatusHistory);
        return serverStatusHistory;
    }

    /**
     *
     * @param serverStatusHistory
     * @return
     */
    @PostMapping("/updateServerStatusHistory")
    private int updateServerStatusHistory(ServerStatusHistory serverStatusHistory){
        return serverStatusHistoryService.updateByPrimaryKeySelective(serverStatusHistory);
    }
}
