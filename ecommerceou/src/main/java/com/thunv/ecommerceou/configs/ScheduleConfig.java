package com.thunv.ecommerceou.configs;

import com.thunv.schedule.TaskSchedule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleConfig {
    @Bean("taskSchedule")
    public TaskSchedule getTaskSchedule(){
        return new TaskSchedule();
    }

}
