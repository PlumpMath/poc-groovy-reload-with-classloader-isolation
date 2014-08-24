package com.littlesquare.services.poc.scheduler

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar

import java.util.concurrent.Executors

/**
 * @author Adam Jordens (adam@jordens.org)
 */
@Configuration
@EnableScheduling
public class SchedulerConfiguration implements SchedulingConfigurer {
    @Override
    void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(5))
    }
}
