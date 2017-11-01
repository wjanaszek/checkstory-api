package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.persistance.model.Photo;
import org.wjanaszek.checkstory.persistance.model.Story;
import org.wjanaszek.checkstory.persistance.model.User;
import org.wjanaszek.checkstory.persistance.repository.PhotoRepository;
import org.wjanaszek.checkstory.persistance.repository.StoryRepository;
import org.wjanaszek.checkstory.persistance.repository.UserRepository;
import org.wjanaszek.checkstory.utils.AuthenticationFacade;
import org.wjanaszek.checkstory.utils.JsonUtils;
import org.wjanaszek.checkstory.utils.Utils;
import org.wjanaszek.checkstory.utils.ValidationUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private AuthenticationFacade authenticationFacade;

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /*
     * Get photo from story
     */
    @RequestMapping(path = "api/stories/{storyId}/photos/{photoId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPhotoForStory(@PathVariable Long storyId, @PathVariable Long photoId) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        Story story = storyRepository.findOne(storyId);
        Photo photo = photoRepository.findOne(photoId);
        if (user != null && story != null && photo != null
            && user.getId() == story.getOwner().getId()
            && user.getId() == photo.getOwner().getId()
            && photo.getStory().getId() == story.getId()) {
                Map<String, String> jsonMap = new HashMap<>();
                JsonUtils.addToMap(photo, jsonMap);
                jsonMap.put("content", Utils.getBase64EncodeImage(photo.getPathToFile()));
                return new ResponseEntity<Map<String, String>>(jsonMap, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Update photo in story
     */
    @RequestMapping(path = "api/stories/{storyId}/photos/{photoId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStoryWithPhoto(@PathVariable Long storyId, @PathVariable Long photoId, @RequestBody Map<String, String> data) throws ParseException {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        Story story = storyRepository.findOne(storyId);
        Photo photo = photoRepository.findOne(photoId);
        if (user != null && story != null && photo != null && user.getId() == story.getOwner().getId() && photo.getOwner().getId() == user.getId()
                && photo.getStory().getId() == story.getId()  && ValidationUtils.validateRequest(data, Photo.class)) {
            if (data.containsKey("storyNumber")) {
                photo.setStory(storyRepository.findOne(Long.valueOf(data.get("storyNumber"))));
            }
            if (data.containsKey("content")) {
                savePhotoOnServer(photo.getPathToFile(), data.get("content"));
            }
            if (data.containsKey("createDate")) {
                photo.setCreateDate(format.parse(data.get("createDate")));
            }
            if (data.containsKey("updateDate") && data.get("updateDate") != null) {
                photo.setUpdateDate(format.parse(data.get("updateDate")));
            }
            if (data.containsKey("originalPhoto")) {
                photo.setOriginalPhoto(data.get("originalPhoto").charAt(0));
            }
            return new ResponseEntity<Photo>(photoRepository.save(photo), responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Add photo to story
     */
    @RequestMapping(path = "api/stories/{storyId}/photos", method = RequestMethod.POST)
    public ResponseEntity<?> addPhotoToStory(@PathVariable Long storyId, @RequestBody Map<String, String> data) throws IOException, ParseException {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        Story story = storyRepository.findOne(storyId);
        if (user != null && story != null && user.getId() == story.getId()
                && ValidationUtils.validateRequest(data, Photo.class)) {

            Photo photo = new Photo();
            photo.setCreateDate(format.parse(data.get("createDate")));
            if (data.containsKey("updateDate") && data.get("updateDate") != null) {
                photo.setUpdateDate(format.parse(data.get("updateDate")));
            }
            photo.setOriginalPhoto(data.get("originalPhoto").charAt(0));
            photo.setOwner(user);
            photo.setStory(story);

            String path = environment.getProperty("uploadsPath").toString();
            path += storyId.toString() + "/photos/" + photo.getCreateDate().hashCode();
            path += "." + data.get("imageType");
            photo.setPathToFile(path);

            photo.setImageType(data.get("imageType"));

            savePhotoOnServer(path, data.get("content"));

            return new ResponseEntity<Photo>(photoRepository.save(photo), responseHeaders, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Get all photos from story
     */
    @RequestMapping(path = "api/stories/{storyId}/photos", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPhotosFromStory(@PathVariable Long storyId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        Story story = storyRepository.findOne(storyId);
        if (user != null && story != null && user.getId() == story.getOwner().getId()) {
            List<Photo> photosList = photoRepository.findAllBelongingToUserByUserId(Long.valueOf(user.getId()), storyId);
            Map<String, List<Map<String, String>>> resultJsonMap = new HashMap<>();
            List<Map<String, String>> tmpList = new ArrayList<>();
            for (Photo p : photosList) {
                Map<String, String> jsonMap = new HashMap<>();
                JsonUtils.addToMap(p, jsonMap);
                jsonMap.put("content", Utils.getBase64EncodeImage(p.getPathToFile()));
                tmpList.add(jsonMap);
            }
            resultJsonMap.put("photos", tmpList);
            return new ResponseEntity<Map<String, List<Map<String, String>>>>(resultJsonMap, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Delete photo from story
     */
    @RequestMapping(path = "api/stories/{storyId}/photos/{photoId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePhotoFromStory(@PathVariable Long storyId, @PathVariable Long photoId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        Story story = storyRepository.findOne(storyId);
        Photo photo = photoRepository.findOne(photoId);
        if (user != null && story != null && photo != null && user.getId() == story.getOwner().getId()
                && photo.getOwner().getId() == user.getId() && photo.getStory().getId() == story.getId()) {
            File file = new File(photoRepository.findOne(photoId).getPathToFile());
            if (!file.delete()) {
                System.out.println("Problem during deleting file");
            }
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
