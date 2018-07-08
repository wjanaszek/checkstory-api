package org.wjanaszek.checkstory.service;

import org.wjanaszek.checkstory.domain.PhotoWithContent;
import org.wjanaszek.checkstory.domain.Story;
import org.wjanaszek.checkstory.exception.BadRequestException;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.request.CreateStoryRequest;
import org.wjanaszek.checkstory.request.CreateUpdatePhotoRequest;
import org.wjanaszek.checkstory.response.StoryDetailResponse;

import java.util.List;

public interface StoryService {
    Story createStoryEntity(CreateStoryRequest createStoryRequest) throws BadRequestException;

    PhotoWithContent createPhotoInStory(Long storyId, CreateUpdatePhotoRequest createPhotoRequest) throws NoResourceFoundException;

    StoryDetailResponse getStoryDetails(Long id);

    void delete(Long id);

    void removePhotoFromStory(Long storyId, Long photoId) throws NoResourceFoundException;

    Story save(Story story);

    List<Story> findByOwnerId(Long id);
}
