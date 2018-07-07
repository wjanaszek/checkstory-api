package org.wjanaszek.checkstory.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.core.env.Environment;
import org.wjanaszek.checkstory.domain.Photo;
import org.wjanaszek.checkstory.domain.Story;
import org.wjanaszek.checkstory.exception.BadRequestException;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.repository.PhotoRepository;
import org.wjanaszek.checkstory.repository.StoryRepository;
import org.wjanaszek.checkstory.request.CreateUpdatePhotoRequest;
import org.wjanaszek.checkstory.service.PhotoService;
import org.wjanaszek.checkstory.utils.TimeProvider;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;

@Slf4j
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private TimeProvider timeProvider;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void createPhoto(CreateUpdatePhotoRequest createPhotoRequest) throws BadRequestException {
        Photo photo = new Photo();
        try {
            photo.setCreateDate(format.parse(createPhotoRequest.getCreateDate()));
        } catch (ParseException e) {
            log.info(e.getMessage());
        }
        photo.setImageType(createPhotoRequest.getImageType());
        photo.setOriginalPhoto(createPhotoRequest.getOriginalPhoto().charAt(0));
        Story story = storyRepository.findOne(createPhotoRequest.getStoryId());
        if (story != null) {
            photo.setStory(story);

            String path = environment.getProperty("uploadsPath").toString();
            path += story.getId().toString() + "photos/" + photo.getId().toString();
            path += "." + createPhotoRequest.getImageType();
            photo.setPathToFile(path);

            try {
                saveImage(path, createPhotoRequest.getContent());
                photoRepository.save(photo);
            } catch (java.io.IOException e) {
                log.error("Error creating file for " + photo.getId() + ", " + photo.getStory().getId());
                e.printStackTrace();
            }
        } else {
            throw new BadRequestException();
        }
    }

    public void updatePhoto(CreateUpdatePhotoRequest updatePhotoRequest) throws BadRequestException, NoResourceFoundException {
        Photo photo = photoRepository.findOne(updatePhotoRequest.getId());
        if (photo != null) {
            if (updatePhotoRequest.getContent() != null) {
                try {
                    saveImage(photo.getPathToFile(), updatePhotoRequest.getContent());
                } catch (IOException e) {
                    log.error("Error updating file for " + photo.getId() + ", " + photo.getStory().getId());
                    e.printStackTrace();
                }
            }
            if (updatePhotoRequest.getOriginalPhoto() != null) {
                photo.setOriginalPhoto(updatePhotoRequest.getOriginalPhoto().charAt(0));
            }
            if (updatePhotoRequest.getImageType() != null) {
                photo.setImageType(updatePhotoRequest.getImageType());
            }
            try {
                photo.setUpdateDate(format.parse(timeProvider.now().toString()));
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
            photoRepository.save(photo);
        } else {
            throw new NoResourceFoundException();
        }
    }

    private void saveImage(String path, String content) throws IOException {
        FileOutputStream imageOutFile = new FileOutputStream(path);
        byte[] imageByteArray = Base64.getDecoder().decode(content);
        imageOutFile.write(imageByteArray);
        imageOutFile.close();
    }

}
