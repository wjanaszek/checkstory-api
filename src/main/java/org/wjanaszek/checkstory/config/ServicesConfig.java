package org.wjanaszek.checkstory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wjanaszek.checkstory.service.PhotoCompareService;
import org.wjanaszek.checkstory.service.PhotoService;
import org.wjanaszek.checkstory.service.StoryService;
import org.wjanaszek.checkstory.service.implementation.PhotoCompareServiceImpl;
import org.wjanaszek.checkstory.service.implementation.PhotoServiceImpl;
import org.wjanaszek.checkstory.service.implementation.StoryServiceImpl;

@Configuration
public class ServicesConfig {

    @Bean
    public StoryService getStoryService() {
        return new StoryServiceImpl();
    }

    @Bean
    public PhotoService getPhotoService() {
        return new PhotoServiceImpl();
    }

    @Bean
    public PhotoCompareService getPhotoCompareService() {
        return new PhotoCompareServiceImpl();
    }

}
