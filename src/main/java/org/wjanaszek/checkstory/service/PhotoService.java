package org.wjanaszek.checkstory.service;

import org.wjanaszek.checkstory.exception.BadRequestException;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.request.CreateUpdatePhotoRequest;

public interface PhotoService {
    void createPhoto(CreateUpdatePhotoRequest createPhotoRequest) throws BadRequestException;
    void updatePhoto(CreateUpdatePhotoRequest updatePhotoRequest) throws BadRequestException, NoResourceFoundException;
}
