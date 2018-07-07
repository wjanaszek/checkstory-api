package org.wjanaszek.checkstory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.domain.Story;
import org.wjanaszek.checkstory.domain.User;
import org.wjanaszek.checkstory.repository.StoryRepository;
import org.wjanaszek.checkstory.service.UserService;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping(value = "api/stories", produces = MediaType.APPLICATION_JSON_VALUE)
public class StoryController {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getUserStories(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(storyRepository.findByOwnerId(user.getId()));
    }

    @PostMapping()
    public ResponseEntity<?> createStory(@RequestBody Story story) {
        return ResponseEntity.ok(storyRepository.save(story));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> removeStory(@PathVariable Long id) {
        storyRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<?> updateStory(@PathVariable Long id, @RequestBody Story story) {
        return ResponseEntity.ok(storyRepository.save(story));
    }

}
