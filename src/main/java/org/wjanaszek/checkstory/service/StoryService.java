package org.wjanaszek.checkstory.service;

import org.wjanaszek.checkstory.domain.PhotoWithContent;
import org.wjanaszek.checkstory.domain.Story;
import org.wjanaszek.checkstory.exception.BadRequestException;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.request.CreateUpdatePhotoRequest;
import org.wjanaszek.checkstory.request.CreateUpdateStoryRequest;
import org.wjanaszek.checkstory.response.StoryDetailResponse;
import org.wjanaszek.checkstory.response.StoryDetailWithPhotosResponse;

import java.util.List;

public interface StoryService {
    Story createStoryEntity(CreateUpdateStoryRequest createStoryRequest) throws BadRequestException;

    PhotoWithContent createPhotoInStory(Long storyId, CreateUpdatePhotoRequest createPhotoRequest) throws NoResourceFoundException;

    StoryDetailResponse getStoryDetails(Long id);

    StoryDetailWithPhotosResponse getStoryDetailsWithPhotos(Long id) throws NoResourceFoundException;

    void removeStoryWithId(Long id);

    void removePhotoFromStory(Long storyId, Long photoId) throws NoResourceFoundException;

    Story save(Story story);

    Story update(Long storyId, CreateUpdateStoryRequest updateStoryRequest) throws NoResourceFoundException;

    PhotoWithContent updatePhotoInStory(
            Long storyId,
            Long photoId,
            CreateUpdatePhotoRequest updatePhotoRequest
    ) throws BadRequestException, NoResourceFoundException;

    List<Story> findByOwnerId(Long id);
}
