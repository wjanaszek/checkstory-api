package org.wjanaszek.checkstory.service;

import org.wjanaszek.checkstory.domain.PhotoWithContent;
import org.wjanaszek.checkstory.domain.Story;
import org.wjanaszek.checkstory.exception.BadRequestException;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.request.CreateUpdatePhotoRequest;

import java.util.List;

public interface PhotoService {
    PhotoWithContent createPhoto(Story story, CreateUpdatePhotoRequest createPhotoRequest) throws NoResourceFoundException;

    List<PhotoWithContent> getPhotosWithContentByStoryId(Long id);

    String getBase64EncodedImage(String path);

    String getBase64EncodedImage(byte[] src);

    void removePhoto(Story story, Long photoId) throws NoResourceFoundException;

    PhotoWithContent updatePhoto(
            Story story,
            Long photoId,
            CreateUpdatePhotoRequest updatePhotoRequest
    ) throws BadRequestException, NoResourceFoundException;
}
