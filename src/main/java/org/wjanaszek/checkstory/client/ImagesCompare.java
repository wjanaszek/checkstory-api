package org.wjanaszek.checkstory.client;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ImagesCompare {

    private static final String IMAGES_COMPARE_SERVICE_URL = "http://localhost:8000";

    public static String getBase64EncodedResultImage(String base64encodedOriginalImage, String originalImageType,
                                                     String base64encodedModifiedImage, String modifiedImageType,
                                                     Boolean resize, Boolean boundingRectangles) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("original", base64encodedOriginalImage);
        requestBody.put("originalImageType", originalImageType);
        requestBody.put("modified", base64encodedModifiedImage);
        requestBody.put("modifiedImageType", modifiedImageType);
        requestBody.put("resize", resize.toString());
        requestBody.put("boundingRectangles", boundingRectangles.toString());
        return restTemplate.postForObject(IMAGES_COMPARE_SERVICE_URL + "/api/images-compare", requestBody, String.class);
    }
}
