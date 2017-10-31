package org.wjanaszek.checkstory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan(basePackages = { "org.wjanaszek.checkstory.persistance.model" })
@EnableJpaRepositories(basePackages = { "org.wjanaszek.checkstory.persistance.repository" })
public class CheckstoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckstoryApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public UserDetails userDetails() {
//		return new
//	}
}
