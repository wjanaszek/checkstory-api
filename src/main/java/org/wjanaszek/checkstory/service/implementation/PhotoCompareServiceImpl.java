package org.wjanaszek.checkstory.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.wjanaszek.checkstory.domain.Photo;
import org.wjanaszek.checkstory.domain.PhotoWithContent;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.request.ComparePhotosRequest;
import org.wjanaszek.checkstory.service.PhotoCompareService;
import org.wjanaszek.checkstory.service.PhotoService;

public class PhotoCompareServiceImpl implements PhotoCompareService {

    @Autowired
    private PhotoService photoService;

    private final String IMAGES_COMPARE_SERVICE_URL = "http://localhost:8000";

    public PhotoWithContent compare(Long firstId, Long secondId, Integer sensitivity) throws NoResourceFoundException {
        PhotoWithContent photoWithContent = null;
        Photo first = photoService.findOne(firstId);
        Photo second = photoService.findOne(secondId);
        if (first == null || second == null) {
            throw new NoResourceFoundException();
        }

        String resultContent = null;    // in base64

        RestTemplate restTemplate = new RestTemplate();

        ComparePhotosRequest body = new ComparePhotosRequest();
        body.setFirst(photoService.getBase64EncodedImage(first.getPathToFile()));
        body.setFirstType(first.getImageType());
        body.setSecond(photoService.getBase64EncodedImage(second.getPathToFile()));
        body.setSecondType(second.getImageType());
        body.setSensitivity(sensitivity);

        resultContent = restTemplate.postForObject(IMAGES_COMPARE_SERVICE_URL + "/api/images-compare", body, String.class);

        photoWithContent.setImageType("jpg");
        photoWithContent.setContent(resultContent);
        return photoWithContent;
    }

}
