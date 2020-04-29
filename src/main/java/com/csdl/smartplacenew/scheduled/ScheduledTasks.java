package com.csdl.smartplacenew.scheduled;


import com.csdl.smartplacenew.mapper.CountMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduledTasks {

    @Resource
    CountMapper countMapper;

    //3.添加定时任务
  //  @Scheduled(cron = "0/5 * * * * ?")
    //每个月1号晚上1清零
    @Scheduled(cron="0 0 1 1 * ?")
    private void configureTasks() {

        System.err.println("每月1号开始清0: " + LocalDateTime.now());

        countMapper.updateMonthlyvisits();

    }
}
