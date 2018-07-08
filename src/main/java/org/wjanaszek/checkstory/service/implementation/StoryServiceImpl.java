package org.wjanaszek.checkstory.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.wjanaszek.checkstory.domain.PhotoWithContent;
import org.wjanaszek.checkstory.domain.Story;
import org.wjanaszek.checkstory.exception.BadRequestException;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.repository.StoryRepository;
import org.wjanaszek.checkstory.request.CreateUpdateStoryRequest;
import org.wjanaszek.checkstory.request.CreateUpdatePhotoRequest;
import org.wjanaszek.checkstory.response.StoryDetailResponse;
import org.wjanaszek.checkstory.service.PhotoService;
import org.wjanaszek.checkstory.service.StoryService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
public class StoryServiceImpl implements StoryService {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private StoryRepository storyRepository;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public PhotoWithContent createPhotoInStory(Long storyId, CreateUpdatePhotoRequest createPhotoRequest) throws NoResourceFoundException {
        Story story = storyRepository.findOne(storyId);
        if (story == null) {
            throw new NoResourceFoundException();
        }
        return photoService.createPhoto(story, createPhotoRequest);
    }

    public Story createStoryEntity(CreateUpdateStoryRequest createStoryRequest) throws BadRequestException {
        if (createStoryRequest.getTitle() == null || createStoryRequest.getLatitude() == null || createStoryRequest.getLongitude() == null) {
            throw new BadRequestException();
        }


        Story story = new Story();

        log.info("Create date: " + createStoryRequest.getCreateDate());

        story.setCreateDate(createStoryRequest.getCreateDate());
        story.setLatitude(createStoryRequest.getLatitude());
        story.setLongitude(createStoryRequest.getLongitude());
        story.setNotes(createStoryRequest.getNotes());
        story.setTitle(createStoryRequest.getTitle());
        return story;
    }

    public StoryDetailResponse getStoryDetails(Long id) {
        return new StoryDetailResponse(photoService.getPhotosWithContentByStoryId(id));
    }

    public Story update(Long storyId, CreateUpdateStoryRequest updateStoryRequest) throws NoResourceFoundException {
        Story story = storyRepository.findOne(storyId);
        if (story == null) {
            throw new NoResourceFoundException();
        }
        story.setTitle(updateStoryRequest.getTitle());
        story.setNotes(updateStoryRequest.getNotes());
        story.setLongitude(updateStoryRequest.getLongitude());
        story.setLatitude(updateStoryRequest.getLatitude());
        story.setCreateDate(updateStoryRequest.getCreateDate());
        return storyRepository.save(story);
    }

    public Story save(Story story) {
        return storyRepository.save(story);
    }

    public List<Story> findByOwnerId(Long id) {
        return storyRepository.findByOwnerId(id);
    }

    public void removeStoryWithId(Long id) {
        storyRepository.delete(id);
    }

    public void removePhotoFromStory(Long storyId, Long photoId) throws NoResourceFoundException {
        Story story = storyRepository.findOne(storyId);
        if (story == null) {
            throw new NoResourceFoundException();
        }
        try {
            photoService.removePhoto(story, photoId);
        } catch (NoResourceFoundException e) {
            throw e;
        }
        log.info("Photo removed");
    }
}
