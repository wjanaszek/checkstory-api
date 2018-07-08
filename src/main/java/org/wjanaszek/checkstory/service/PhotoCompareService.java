package org.wjanaszek.checkstory.service;

import org.wjanaszek.checkstory.domain.PhotoWithContent;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;

public interface PhotoCompareService {

    PhotoWithContent compare(Long firstId, Long secondId, Integer sensitivity) throws NoResourceFoundException;

}
