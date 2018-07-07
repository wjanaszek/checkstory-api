package org.wjanaszek.checkstory.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.wjanaszek.checkstory.domain.Photo;
import org.wjanaszek.checkstory.repository.StoryRepository;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Map;

@Slf4j
public class PhotoUtils {

//    @Autowired
//    private PhotoRepository photoRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private static Environment environment;

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static void addToMap(Photo photo, Map<String, String> jsonMap) {
        jsonMap.put("id", photo.getId().toString());
        jsonMap.put("storyNumber", photo.getStory().getId().toString());
        jsonMap.put("originalPhoto", photo.getOriginalPhoto().toString());
        jsonMap.put("createDate", photo.getCreateDate().toString());
        jsonMap.put("imageType", photo.getImageType());
        if (photo.getUpdateDate() != null) {
            jsonMap.put("updateDate", photo.getUpdateDate().toString());
        } else {
            jsonMap.put("updateDate", "");
        }
    }

    public Photo mapToModelCreate(Map<String, String> data, Long storyId) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Photo photo = new Photo();
        photo.setCreateDate(format.parse(data.get("createDate")));
        if (!data.get("updateDate").equals("")) {
            photo.setUpdateDate(format.parse(data.get("updateDate")));
        }
        photo.setOriginalPhoto(data.get("originalPhoto").charAt(0));
//        photo.setOwner(userRepository.findOne(Long.valueOf(data.get("owner_id"))));
        photo.setStory(storyRepository.findOne(Long.valueOf(data.get("story_number"))));
//        photoRepository.save(photo);

        String path = environment.getProperty("uploadsPath").toString();
        path += storyId.toString() + "photos/" + photo.getId().toString();
        path += "." + data.get("imageType");
        photo.setPathToFile(path);
        log.info("generated path: " + path);

        try {
            FileOutputStream imageOutFile = new FileOutputStream(path);
            byte[] imageByteArray = Base64.getDecoder().decode(data.get("content"));
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        } catch (java.io.IOException e) {
            log.error("Error creating file for " + photo.getId() + ", " + photo.getStory().getId());
            e.printStackTrace();
        }
        return photo;
    }

    public void mapToModelUpdate(Photo photo, Map<String, String> data, Long storyId) throws ParseException {
        if (data.containsKey("story_number")) {
            photo.setStory(storyRepository.findOne(Long.valueOf(data.get("story_number"))));
        }
        if (data.containsKey("content")) {
            try {
                FileOutputStream imageOutFile = new FileOutputStream(photo.getPathToFile());
                byte[] imageByteArray = Base64.getDecoder().decode(data.get("content"));
                imageOutFile.write(imageByteArray);
                imageOutFile.close();
            } catch (java.io.IOException e) {
                log.error("Error creating file for " + photo.getId() + ", " + photo.getStory().getId());
                e.printStackTrace();
            }
        }
        if (data.containsKey("createDate")) {
            photo.setCreateDate(format.parse(data.get("createDate")));
        }
        if (data.containsKey("updateDate")) {
            photo.setUpdateDate(format.parse(data.get("updateDate")));
        }
    }
}
