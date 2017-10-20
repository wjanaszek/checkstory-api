package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.wjanaszek.checkstory.model.User;
import org.wjanaszek.checkstory.services.UserService;

import java.net.URI;

@RestController
public class UserRestController {

    private final UserService userService;

    @Autowired
    UserRestController(UserService userService) {
        this.userService = userService;
    }

    /*
     *   Create a user
     */
    @RequestMapping(path = "api/users", method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody User input) {
        User resultUser = userService.saveUser(new User(input.getLogin(), input.getEmail(), input.getPassword()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(resultUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    /*
     *   Get list of users
     */
    @RequestMapping(path = "api/users", method = RequestMethod.GET)
    ResponseEntity<Iterable<User>> getUsers() {
        return new ResponseEntity<Iterable<User>>(this.userService.listAllUsers(), null, HttpStatus.OK);
    }

    /*
     * Get user by id
     */
    @RequestMapping(path = "api/users/{id}", method = RequestMethod.GET)
    ResponseEntity<User> getUserById(@PathVariable Long id) {
        User searchedUser = this.userService.getUserById(id);
        if (searchedUser != null) {
            return new ResponseEntity<User>(searchedUser, null, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(null, null, HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Update user with id
     */
    @RequestMapping(path = "api/users/{id}", method = RequestMethod.PUT)
    ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User input) {
        if (!id.equals(input.getId())) {
            return new ResponseEntity<User>(input, null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<User>(this.userService.saveUser(input), null, HttpStatus.OK);
        }
    }

    /*
     * Delete user with id
     */
    @RequestMapping(path = "api/users/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> removeUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
        return new ResponseEntity<Object>(null, null, HttpStatus.OK);
    }
}
