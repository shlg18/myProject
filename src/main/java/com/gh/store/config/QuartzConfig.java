package com.gh.store.config;

import com.gh.store.jobs.MyFirstJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail jobDetail(){
        return JobBuilder.newJob(MyFirstJob.class).storeDurably().build();
    }
   /* @Bean
    public Trigger trigger(){
        SimpleScheduleBuilder scheduleBuilder=SimpleScheduleBuilder.simpleSchedule()
                //每一秒执行一次
                .withIntervalInSeconds(1)
                .repeatForever(); //永久重复，一直执行下去
        return TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")
                .withSchedule(scheduleBuilder)
                .forJob(jobDetail())
                .build();
    }*/

    // 每两秒触发一次任务
    @Bean
    public Trigger trigger(){
        return TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? *"))
                .forJob(jobDetail())
                .build();
    }
}
