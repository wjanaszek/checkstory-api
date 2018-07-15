package org.wjanaszek.checkstory.service;

import org.wjanaszek.checkstory.domain.Photo;
import org.wjanaszek.checkstory.domain.PhotoWithContent;
import org.wjanaszek.checkstory.domain.Story;
import org.wjanaszek.checkstory.exception.BadRequestException;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.request.CreateUpdatePhotoRequest;

import java.io.File;
import java.util.List;

public interface PhotoService {
    PhotoWithContent createPhoto(Story story, CreateUpdatePhotoRequest createPhotoRequest) throws NoResourceFoundException;

    String generateBase64Thumbnail(File file);

    String generateBase64Thumbnail(String base64String, Integer targetSize);

    List<PhotoWithContent> getPhotosWithContentByStoryId(Long id);

    String getBase64EncodedImage(String path);

    String getBase64EncodedImage(byte[] src);

    Photo findOne(Long id);

    void removePhoto(Story story, Long photoId) throws NoResourceFoundException;

    PhotoWithContent updatePhoto(
            Story story,
            Long photoId,
            CreateUpdatePhotoRequest updatePhotoRequest
    ) throws BadRequestException, NoResourceFoundException;
}
