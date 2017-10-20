package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(path = "api/stories", method = RequestMethod.GET)
    public ResponseEntity<?> getStories(@RequestBody Long userId) {
        if (userRepository.exists(userId)) {
            return new ResponseEntity<Iterable<Story>>(storyRepository.findAllByUserId(userId), null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "api/stories", method = RequestMethod.POST)
    public ResponseEntity<?> addStory(@RequestBody Story story) {
        if (userRepository.exists(story.getOwner().getId())) {
            return new ResponseEntity<Story>(storyRepository.save(story), null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "api/stories/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStoryById(@RequestParam Long id, @RequestBody Long userId) {
        if (userRepository.exists(userId)) {
            if (storyRepository.exists(id)) {
                return new ResponseEntity<Story>(storyRepository.findOne(id), null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }
    }

//    Potrzebne?
//    @RequestMapping(path = "api/stories/{id}", method = RequestMethod.POST)
//    public ResponseEntity<?> addStory(@RequestParam Long )

    @RequestMapping(path = "api/stories/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStory(@RequestParam Long id, @RequestBody Story story) {
        if (userRepository.exists(story.getOwner().getId())) {
            if (storyRepository.exists(id)) {
                return new ResponseEntity<Story>(storyRepository.save(story), null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "api/stories/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStory(@RequestParam Long id) {
        if (storyRepository.exists(id)) {
            storyRepository.delete(id);
            return new ResponseEntity<>(null, null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
    }
}
