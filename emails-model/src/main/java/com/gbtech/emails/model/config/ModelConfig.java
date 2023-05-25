package com.gbtech.emails.model.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = { "com.gbtech.emails.model" })
@EnableJpaRepositories(basePackages = { "com.gbtech.emails.model.repository" })
@EntityScan(basePackages = "com.gbtech.emails.model.repository.entity")
public class ModelConfig {

}
