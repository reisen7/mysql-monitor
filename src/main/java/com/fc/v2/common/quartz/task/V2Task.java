package com.fc.v2.common.quartz.task;
import cn.hutool.core.date.DateUtil;
import com.fc.v2.model.monitor.ServerStatusHistory;
import com.fc.v2.model.monitor.ServerStatusHistoryExample;
import com.fc.v2.service.monitor.ServerStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 *测试类
 * @CLASSNAME   V2Task
 * @Description 定时调度具体工作类
 * @Auther Jan  橙寂
 * @DATE 2019/9/2 0002 15:33
 */
@Order(value = 3) // 数字越小，越先执行
@Component("v2Task")
public class V2Task {
    @Autowired
    private ServerStatusHistoryService serverStatusHistoryService;
    /**
     * 无参的任务
     */
    public void runTask1()
    {
        ServerStatusHistoryExample example = new ServerStatusHistoryExample();
        example.createCriteria().andCreateTimeEqualTo(new Date());
        List<ServerStatusHistory> serverStatusHistoryList = serverStatusHistoryService.selectByExample(example);
        example.setOrderByClause(" create_time desc limit 1");
        System.out.println(">>>>>>>>>> 【Result】<<<<<<<<<< ");
        System.out.println(serverStatusHistoryList.size());
        System.out.println("正在执行定时任务，无参方法 runTask1 ");
    }

    /**
     * 有参任务
     * 目前仅执行常见的数据类型  Integer Long  带L  string  带 ''  bool Double 带 d
     * @param a
     * @param b
     */
    public void runTask2(Integer a,Long b,String c,Boolean d,Double e)
    {
        System.out.println("正在执行定时任务，带多个参数的方法 runTask2 "+a+"   "+b+" "+c+"  "+d+" "+e+"执行时间:"+DateUtil.now());
    }

    /**
     * 定时监控获取数据
     *
     */
    public void scheduledDatabaseCheck()
    {
        System.out.println("正在执行定时任务，无参方法 : scheduledDatabaseCheck ");
        UpdateMysqlStatusTask task = new UpdateMysqlStatusTask();
        task.execute();
        System.out.println("执行完成");
    }

}
