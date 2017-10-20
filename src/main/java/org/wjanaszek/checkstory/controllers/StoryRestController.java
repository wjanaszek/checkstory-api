package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.persistance.model.Story;
import org.wjanaszek.checkstory.persistance.repository.StoryRepository;
import org.wjanaszek.checkstory.persistance.repository.UserRepository;

//TODO implement/check
@RestController
public class StoryRestController {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    @CrossOrigin
    @RequestMapping(path = "api/stories", method = RequestMethod.GET)
    public ResponseEntity<?> getStories(@RequestBody Long userId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", environment.getProperty("allowedOrigin"));
        if (userRepository.exists(userId)) {
//            return new ResponseEntity<Iterable<Story>>(storyRepository.findAllByUserId(userId), null, HttpStatus.OK);
            return new ResponseEntity<Iterable<Story>>(null, responseHeaders, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @RequestMapping(path = "api/stories", method = RequestMethod.POST)
    public ResponseEntity<?> addStory(@RequestBody Story story) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", environment.getProperty("allowedOrigin"));
        if (userRepository.exists(story.getOwner().getId())) {
            return new ResponseEntity<Story>(storyRepository.save(story), responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @RequestMapping(path = "api/stories/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStoryById(@RequestParam Long id, @RequestBody Long userId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", environment.getProperty("allowedOrigin"));
        if (userRepository.exists(userId)) {
            if (storyRepository.exists(id)) {
                return new ResponseEntity<Story>(storyRepository.findOne(id), null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

//    Potrzebne?
//    @RequestMapping(path = "api/stories/{id}", method = RequestMethod.POST)
//    public ResponseEntity<?> addStory(@RequestParam Long )

    @CrossOrigin
    @RequestMapping(path = "api/stories/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStory(@RequestParam Long id, @RequestBody Story story) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", environment.getProperty("allowedOrigin"));
        if (userRepository.exists(story.getOwner().getId())) {
            if (storyRepository.exists(id)) {
                return new ResponseEntity<Story>(storyRepository.save(story), null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @RequestMapping(path = "api/stories/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStory(@RequestParam Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", environment.getProperty("allowedOrigin"));
        if (storyRepository.exists(id)) {
            storyRepository.delete(id);
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }
}
