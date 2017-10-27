package org.wjanaszek.checkstory.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.wjanaszek.checkstory.persistance.model.Photo;
import org.wjanaszek.checkstory.persistance.repository.PhotoRepository;
import org.wjanaszek.checkstory.persistance.repository.StoryRepository;
import org.wjanaszek.checkstory.persistance.repository.UserRepository;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Map;

@Component
public class MappingUtils {

    @Autowired
    private static UserRepository userRepository;

    @Autowired
    private static PhotoRepository photoRepository;

    @Autowired
    private static StoryRepository storyRepository;

    @Autowired
    private static Environment environment;

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static Photo mapToModelCreate(Map<String, String> data, Long storyId) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Photo photo = new Photo();
        photo.setCreateDate(format.parse(data.get("createDate")));
        if (!data.get("updateDate").equals("")) {
            photo.setUpdateDate(format.parse(data.get("updateDate")));
        }
        photo.setOriginalPhoto(data.get("originalPhoto").charAt(0));
        photo.setOwner(userRepository.findOne(Long.valueOf(data.get("owner_id"))));
        photo.setStory(storyRepository.findOne(Long.valueOf(data.get("story_number"))));
        photoRepository.save(photo);

        String path = environment.getProperty("uploadsPath").toString();
        path += storyId.toString() + "photos/" + photo.getId().toString();
        path += "." + data.get("imageType");
        photo.setPathToFile(path);
        System.out.println("generated path: " + path);

        try {
            FileOutputStream imageOutFile = new FileOutputStream(path);
            byte[] imageByteArray = Base64.getDecoder().decode(data.get("content"));
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        }
        catch (java.io.IOException e) {
            System.out.println("Error creating file for " + photo.getId() + ", " + photo.getOwner().getId() + ", " + photo.getStory().getId());
            e.printStackTrace();
        }
        return photo;
    }

    // TODO add moving files from one story to another feature
    public static void mapToModelUpdate(Photo photo, Map<String, String> data, Long storyId) throws ParseException {
        if (data.containsKey("story_number")) {
            photo.setStory(storyRepository.findOne(Long.valueOf(data.get("story_number"))));
        }
        if (data.containsKey("content")) {
            try {
                FileOutputStream imageOutFile = new FileOutputStream(photo.getPathToFile());
                byte[] imageByteArray = Base64.getDecoder().decode(data.get("content"));
                imageOutFile.write(imageByteArray);
                imageOutFile.close();
            }
            catch (java.io.IOException e) {
                System.out.println("Error creating file for " + photo.getId() + ", " + photo.getOwner().getId() + ", " + photo.getStory().getId());
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

    public static void setUserRepository(UserRepository userRepository) {
        MappingUtils.userRepository = userRepository;
    }

    public static void setPhotoRepository(PhotoRepository photoRepository) {
        MappingUtils.photoRepository = photoRepository;
    }

    public static void setStoryRepository(StoryRepository storyRepository) {
        MappingUtils.storyRepository = storyRepository;
    }

    public static void setEnvironment(Environment environment) {
        MappingUtils.environment = environment;
    }
}
