package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.persistance.model.Photo;
import org.wjanaszek.checkstory.persistance.repository.PhotoRepository;
import org.wjanaszek.checkstory.persistance.repository.StoryRepository;
import org.wjanaszek.checkstory.persistance.repository.UserRepository;
import org.wjanaszek.checkstory.utils.JsonUtils;
import org.wjanaszek.checkstory.utils.ValidationUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StoryDetailRestController {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /*
     * Get photo from story
     */
    @CrossOrigin
    @RequestMapping(path = "api/stories/{storyId}/photos/{photoId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPhotoForStory(@PathVariable Long storyId, @PathVariable Long photoId) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (storyRepository.exists(storyId)) {
            if (photoRepository.exists(photoId)) {
                Map<String, String> jsonMap = new HashMap<>();
                Photo photo = photoRepository.findOne(photoId);
                JsonUtils.addToMap(photo, jsonMap);
                File file = null;
                try {
                    file = ResourceUtils.getFile(photo.getPathToFile());
                } catch (FileNotFoundException e) {
                    System.out.println("File not found for " + photo.getId() + ", " + photo.getOwner().getId() + ", " + photo.getStory().getId());
                    e.printStackTrace();
                }
                String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath()));
                jsonMap.put("content", encodeImage);
                return new ResponseEntity<Map<String, String>>(jsonMap, responseHeaders, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Update photo in story
     */
    @CrossOrigin
    @RequestMapping(path = "api/stories/{storyId}/photos/{photoId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStoryWithPhoto(@PathVariable Long storyId, @PathVariable Long photoId, @RequestBody Map<String, String> data) throws ParseException {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (storyRepository.exists(storyId) && photoRepository.exists(photoId) && ValidationUtils.validateRequest(data, Photo.class)) {
            Photo photo = photoRepository.findOne(photoId);
            if (data.containsKey("story_number")) {
                photo.setStory(storyRepository.findOne(Long.valueOf(data.get("story_number"))));
            }
            if (data.containsKey("content")) {
                savePhotoOnServer(photo.getPathToFile(), data.get("content"));
            }
            if (data.containsKey("createDate")) {
                photo.setCreateDate(format.parse(data.get("createDate")));
            }
            if (data.containsKey("updateDate")) {
                photo.setUpdateDate(format.parse(data.get("updateDate")));
            }
            return new ResponseEntity<Photo>(photoRepository.save(photo), responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Add photo to story
     */
    @CrossOrigin
    @RequestMapping(path = "api/stories/{storyId}/photos", method = RequestMethod.POST)
    public ResponseEntity<?> addPhotoToStory(@PathVariable Long storyId, @RequestBody Map<String, String> data) throws IOException, ParseException {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (storyRepository.exists(storyId) && userRepository.exists(Long.valueOf(data.get("owner_id")))
                && ValidationUtils.validateRequest(data, Photo.class)) {

            Photo photo = new Photo();
            photo.setCreateDate(format.parse(data.get("createDate")));
            if (!data.get("updateDate").equals("")) {
                photo.setUpdateDate(format.parse(data.get("updateDate")));
            }
            photo.setOriginalPhoto(data.get("originalPhoto").charAt(0));
            photo.setOwner(userRepository.findOne(Long.valueOf(data.get("owner_id"))));
            photo.setStory(storyRepository.findOne(Long.valueOf(data.get("story_number"))));

            String path = environment.getProperty("uploadsPath").toString();
            path += storyId.toString() + "/photos/" + photo.getCreateDate().hashCode();
            path += "." + data.get("imageType");
            photo.setPathToFile(path);

            savePhotoOnServer(path, data.get("content"));

            return new ResponseEntity<Photo>(photoRepository.save(photo), responseHeaders, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Delete photo from story
     */
    @CrossOrigin
    @RequestMapping(path = "api/stories/{storyId}/photos/{photoId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePhotoFromStory(@PathVariable Long storyId, @PathVariable Long photoId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (storyRepository.exists(storyId) && photoRepository.exists(photoId)) {
            photoRepository.delete(photoId);
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    private void savePhotoOnServer(String path, String base64Content) {
        try {
            FileOutputStream imageOutFile = new FileOutputStream(path);
            byte[] imageByteArray = Base64.getDecoder().decode(base64Content);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
