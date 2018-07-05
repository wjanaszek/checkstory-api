package org.wjanaszek.checkstory.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.wjanaszek.checkstory.repository")
@EntityScan(basePackages = "org.wjanaszek.checkstory.domain")
public class DatasourceConfig {
}
