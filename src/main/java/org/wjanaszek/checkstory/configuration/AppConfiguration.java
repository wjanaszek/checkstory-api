package org.wjanaszek.checkstory.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.wjanaszek.checkstory.utils.MappingUtils;

@Configuration
@ComponentScan(basePackages = "org.wjanaszek.checkstory.utils")
public class AppConfiguration {

    @Bean
    public MappingUtils mappingUtils() {
        return new MappingUtils();
    }
}
