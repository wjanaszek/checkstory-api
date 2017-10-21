package org.wjanaszek.checkstory.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//TODO
@RestController
public class StoryDetailRestController {

    /*
     * Get photo from story
     */
    @CrossOrigin
    @RequestMapping(path = "api/stories/{storyId}/photos/{photoId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPhotoForStory(@PathVariable Long storyId, @PathVariable Long photoId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.OK);
    }

    /*
     * Update photo in story
     */
    @CrossOrigin
    @RequestMapping(path = "api/stories/{storyId}/photos/{photoId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStoryWithPhoto(@PathVariable Long storyId, @PathVariable Long photoId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.OK);
    }

    /*
     * Add photo to story
     */
    @CrossOrigin
    @RequestMapping(path = "api/stories/{storyId}/photos", method = RequestMethod.POST)
    public ResponseEntity<?> addPhotoToStory(@PathVariable Long storyId, @RequestBody Object data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.OK);
    }

    /*
     * Delete photo from story
     */
    @CrossOrigin
    @RequestMapping(path = "api/stories/{storyId}/photos/{photoId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePhotoFromStory(@PathVariable Long storyId, @PathVariable Long photoId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.OK);
    }
}
