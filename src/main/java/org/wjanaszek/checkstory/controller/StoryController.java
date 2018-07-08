package org.wjanaszek.checkstory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.domain.Story;
import org.wjanaszek.checkstory.domain.User;
import org.wjanaszek.checkstory.exception.BadRequestException;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.request.CreateStoryRequest;
import org.wjanaszek.checkstory.request.CreateUpdatePhotoRequest;
import org.wjanaszek.checkstory.service.StoryService;
import org.wjanaszek.checkstory.service.UserService;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping(value = "api/stories", produces = MediaType.APPLICATION_JSON_VALUE)
public class StoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private StoryService storyService;

    @GetMapping()
    public ResponseEntity<?> getUserStories(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(storyService.findByOwnerId(user.getId()));
    }

    @PostMapping()
    public ResponseEntity<?> createStory(@RequestBody CreateStoryRequest request, Principal principal) throws BadRequestException {
        User user = userService.findByUsername(principal.getName());
        if (user != null) {
            Story story = storyService.createStoryEntity(request);
            story.setOwner(user);
            return ResponseEntity.ok(storyService.save(story));
        } else {
            throw new BadRequestException();
        }
    }

    @PostMapping(value = "{id}")
    public ResponseEntity<?> createPhotoInStory(
            @PathVariable Long id,
            @RequestBody CreateUpdatePhotoRequest createPhotoRequest
    ) throws NoResourceFoundException {
        return ResponseEntity.ok(storyService.createPhotoInStory(id, createPhotoRequest));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> removeStory(@PathVariable Long id) {
        storyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<?> updateStory(@PathVariable Long id, @RequestBody Story story) {
        return ResponseEntity.ok(storyService.save(story));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getStoryDetails(@PathVariable Long id) {
        return ResponseEntity.ok(storyService.getStoryDetails(id));
    }

}
