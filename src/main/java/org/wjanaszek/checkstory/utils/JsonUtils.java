package org.wjanaszek.checkstory.utils;

import org.wjanaszek.checkstory.persistance.model.Photo;

import java.util.Map;

public class JsonUtils {

    public static void addToMap(Photo photo, Map<String, String> jsonMap) {
        jsonMap.put("id", photo.getId().toString());
        jsonMap.put("storyNumber", photo.getStory().getId().toString());
        jsonMap.put("owner_id", photo.getOwner().getId().toString());
        jsonMap.put("originalPhoto", photo.getOriginalPhoto().toString());
        jsonMap.put("createDate", photo.getCreateDate().toString());
        jsonMap.put("imageType", photo.getImageType());
        if (photo.getUpdateDate() != null) {
            jsonMap.put("updateDate", photo.getUpdateDate().toString());
        } else {
            jsonMap.put("updateDate", "");
        }
    }
}
