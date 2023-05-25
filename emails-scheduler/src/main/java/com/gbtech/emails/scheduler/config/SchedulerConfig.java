package com.gbtech.emails.scheduler.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The Emails Scheduler Config.
 */
@Configuration
@EnableScheduling
@ComponentScan("com.gbtech.scheduler")
public class SchedulerConfig {

}
