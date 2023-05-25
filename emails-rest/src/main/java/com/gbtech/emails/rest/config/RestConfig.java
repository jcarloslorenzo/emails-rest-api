package com.gbtech.emails.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The Emails REST API Config.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.gbtech.emails.rest" })
public class RestConfig {

}
