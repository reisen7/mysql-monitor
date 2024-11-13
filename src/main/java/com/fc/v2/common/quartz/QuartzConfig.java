package com.fc.v2.common.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

/**
 * @CLASSNAME QuartzConfig
 * @Description  Quartz配置类
 * @Auther Jan  橙寂
 * @DATE 2019/9/2 0002 15:21
 */
@Configuration
public class QuartzConfig {

    @Autowired
    private MyJobFactory jobFactory;

    /**
     * 初始注入scheduler
     * @return
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws IOException, SchedulerException {
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }

//    @Bean(name = "SchedulerFactory")
//    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setOverwriteExistingJobs(true);
//        //延时启动  应用程序 启动 5秒后
//        factory.setStartupDelay(1);
//        //替换从spring创建实例 使得spring注入 正常运行
//        factory.setJobFactory(jobFactory);
//        return factory;
//    }
}
