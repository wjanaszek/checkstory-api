package org.wjanaszek.checkstory.utils;

import org.wjanaszek.checkstory.persistance.model.Photo;

import java.util.Map;

public class JsonUtils {

    public static void addToMap(Photo photo, Map<String, String> jsonMap) {
        jsonMap.put("photoNumber", photo.getId().toString());
        jsonMap.put("story", photo.getStory().getId().toString());
        jsonMap.put("owner", photo.getOwner().getId().toString());
        jsonMap.put("originalPhoto", photo.getOriginalPhoto().toString());
        jsonMap.put("pathToFile", photo.getPathToFile());
        jsonMap.put("createDate", photo.getCreateDate().toString());
        if (photo.getUpdateDate() != null) {
            jsonMap.put("updateDate", photo.getUpdateDate().toString());
        } else {
            jsonMap.put("updateDate", "");
        }
    }
}
