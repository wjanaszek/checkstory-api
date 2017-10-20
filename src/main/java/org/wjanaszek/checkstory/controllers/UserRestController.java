package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.wjanaszek.checkstory.persistance.model.User;
import org.wjanaszek.checkstory.persistance.repository.UserRepository;

import java.net.URI;

@RestController
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    /*
     *   Create a user
     */
    @RequestMapping(path = "api/users", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody User input) {
        User resultUser = userRepository.save(new User(input.getLogin(), input.getEmail(), input.getPassword()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(resultUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    /*
     *   Get list of users
     */
    @RequestMapping(path = "api/users", method = RequestMethod.GET)
//    ResponseEntity<Iterable<User>> getUsers() {
//        return new ResponseEntity<Iterable<User>>(this.userRepository.findAll(), null, HttpStatus.OK);
//    }
    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    /*
     * Get user by id
     */
    @RequestMapping(path = "api/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User searchedUser = this.userRepository.findOne(id);
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
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User input) {
        if (!id.equals(input.getId())) {
            return new ResponseEntity<User>(input, null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<User>(this.userRepository.save(input), null, HttpStatus.OK);
        }
    }

    /*
     * Delete user with id
     */
    @RequestMapping(path = "api/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeUser(@PathVariable Long id) {
        if(this.userRepository.exists(id)) {
            this.userRepository.delete(id);
            return new ResponseEntity<Object>(null, null, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(null, null, HttpStatus.BAD_REQUEST);
        }
    }
}
