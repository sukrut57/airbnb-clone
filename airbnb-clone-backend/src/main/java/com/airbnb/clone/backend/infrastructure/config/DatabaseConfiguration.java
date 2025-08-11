package com.airbnb.clone.backend.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(basePackages = "com.airbnb.clone.backend.user.adapter.out.persistence.repository")
@EnableTransactionManagement
@EnableJpaAuditing
public class DatabaseConfiguration {
}
