package org.wjanaszek.checkstory.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"org.wjanaszek.checkstory.persistance.model"})
@EnableJpaRepositories(basePackages = {"org.wjanaszek.checkstory.persistance.repository"})
@EnableTransactionManagement
public class RepositoryConfiguration { }
