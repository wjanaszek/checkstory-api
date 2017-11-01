package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.persistance.model.Story;
import org.wjanaszek.checkstory.persistance.model.User;
import org.wjanaszek.checkstory.persistance.repository.StoryRepository;
import org.wjanaszek.checkstory.persistance.repository.UserRepository;
import org.wjanaszek.checkstory.utils.AuthenticationFacade;

import java.util.List;
import java.util.Set;

@RestController
public class StoryRestController {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    /*
     * Get all stories of user with specified userId
     */
    @RequestMapping(path = "api/stories", method = RequestMethod.GET)
    public ResponseEntity<?> getStories() {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        System.out.println("haha " + authenticationFacade.getAuthentication().getPrincipal());
        if (user != null) {
            return new ResponseEntity<List<Story>>(storyRepository.findAllBelongingToUserByUserId(user.getId()), responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Add story to user stories
     */
    @RequestMapping(path = "api/stories", method = RequestMethod.POST)
    public ResponseEntity<?> addStory(@RequestBody Story story) {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        if (user != null) {
            story.setOwner(user);
            return new ResponseEntity<Story>(storyRepository.save(story), responseHeaders, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Get story with specified id (to detail view for example)
     */
    @RequestMapping(path = "api/stories/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStoryById(@PathVariable Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        if (user != null) {
            Story story = storyRepository.findOne(id);
            if (story.getOwner().getId() != user.getId()) {
                return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<Story>(story, responseHeaders, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

//    Potrzebne?
//    @RequestMapping(path = "api/stories/{id}", method = RequestMethod.POST)
//    public ResponseEntity<?> addStory(@RequestParam Long )

    /*
     * Update story with id
     */
    @RequestMapping(path = "api/stories/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStory(@PathVariable Long id, @RequestBody Story story) {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        if (user != null && userRepository.exists(story.getOwner().getId()) && story.getOwner().getId() == user.getId()) {
            if (storyRepository.exists(id)) {
                return new ResponseEntity<Story>(storyRepository.save(story), responseHeaders, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Delete story with id
     */
    @RequestMapping(path = "api/stories/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStory(@PathVariable Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        Story story = storyRepository.findOne(id);
        if (user != null && story != null && user.getId() == story.getOwner().getId()) {
            storyRepository.delete(id);
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }
}
