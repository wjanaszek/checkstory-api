package org.wjanaszek.checkstory.service;

import org.wjanaszek.checkstory.domain.Story;
import org.wjanaszek.checkstory.exception.BadRequestException;
import org.wjanaszek.checkstory.request.CreateStoryRequest;

public interface StoryService {
    Story createStoryEntity(CreateStoryRequest createStoryRequest) throws BadRequestException;
}
