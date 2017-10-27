package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.persistance.model.Photo;
import org.wjanaszek.checkstory.persistance.repository.PhotoRepository;
import org.wjanaszek.checkstory.persistance.repository.StoryRepository;

//TODO
@RestController
public class StoryDetailRestController {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private PhotoRepository photoRepository;

    /*
     * Get photo from story
     */
    @CrossOrigin
    @RequestMapping(path = "api/stories/{storyId}/photos/{photoId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPhotoForStory(@PathVariable Long storyId, @PathVariable Long photoId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (storyRepository.exists(storyId)) {
            if (photoRepository.exists(photoId)) {
                return new ResponseEntity<Photo>(photoRepository.findOne(photoId), responseHeaders, HttpStatus.OK);
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
    public ResponseEntity<?> updateStoryWithPhoto(@PathVariable Long storyId, @PathVariable Long photoId, @RequestBody Photo input) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (storyRepository.exists(storyId)) {
            if (photoRepository.exists(photoId)) {
                return new ResponseEntity<Photo>(photoRepository.save(input), responseHeaders, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Add photo to story (release)
     */
    @CrossOrigin
    @RequestMapping(path = "api/stories/{storyId}/photos", method = RequestMethod.POST)
    public ResponseEntity<?> addPhotoToStory(@PathVariable Long storyId, @RequestBody Photo data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (storyRepository.exists(storyId)) {
            return new ResponseEntity<Photo>(photoRepository.save(data), responseHeaders, HttpStatus.CREATED);
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
        if (storyRepository.exists(storyId)) {
            if (photoRepository.exists(photoId)) {
                photoRepository.delete(photoId);
                return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }
}
