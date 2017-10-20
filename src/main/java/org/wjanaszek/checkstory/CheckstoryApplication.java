package org.wjanaszek.checkstory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "org.wjanaszek.checkstory.model" })
@EnableJpaRepositories(basePackages = { "org.wjanaszek.checkstory.repositories" })
public class CheckstoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckstoryApplication.class, args);
	}
}
