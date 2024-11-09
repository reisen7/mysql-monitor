package com.fc.v2;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fc.v2.model.monitor.MonitorServer;
import com.fc.v2.model.monitor.MonitorServerExample;
import com.fc.v2.model.monitor.ServerStatusHistory;
import com.fc.v2.model.monitor.ServerStatusHistoryExample;
import com.fc.v2.service.monitor.ServerStatusHistoryService;
import com.fc.v2.util.HttpUtil;
import com.fc.v2.util.SnowflakeIdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.alibaba.fastjson.JSON;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName com.fc.v2.ShardingTestApplication
 * @Author reisen
 * @Description
 * @date 2024年11月06日
 **/
@SpringBootTest
@ContextConfiguration
public class ShardingTestApplication {

    @Autowired
    private ServerStatusHistoryService serverStatusHistoryService;

    @Test
    void saveTest() {
        Date date = new Date();
        ServerStatusHistory statusHistory = new ServerStatusHistory();
        statusHistory.setId(SnowflakeIdWorker.getUUID());
        Calendar calendar = Calendar.getInstance(); // 获取一个Calendar实例
        calendar.setTime(date); // 设置Calendar的时间为当前Date对象的时间
        calendar.add(Calendar.MONTH, 1); // 给月份加1
        statusHistory.setCreateTime(calendar.getTime());
        serverStatusHistoryService.insertSelective(statusHistory);
    }

    @Test
    void listTest() {
//        PageHelper.startPage(1, 1);
        ServerStatusHistoryExample example = new ServerStatusHistoryExample();
        List<ServerStatusHistory> serverStatusHistoryList = serverStatusHistoryService.selectByExample(example);
        System.out.println(">>>>>>>>>> 【Result】<<<<<<<<<< ");
        System.out.println(serverStatusHistoryList.size());
    }

    @Test
    public void doPostTest(){
        MonitorServerExample monitorServerExample = new MonitorServerExample();
        String jsonString = JSON.toJSONString(monitorServerExample);
        String message = HttpUtil.doPost("http://localhost:8080/ServerStatusHistoryController/getListMonitorServer", JSONObject.parseObject(jsonString));
        List<MonitorServer> MonitorServerList = JSON.parseObject(message, new TypeReference<List<MonitorServer>>(){});
        System.out.println(MonitorServerList.get(0));
    }
}
