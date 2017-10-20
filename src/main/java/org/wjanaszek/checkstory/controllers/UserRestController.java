package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.persistance.model.User;
import org.wjanaszek.checkstory.persistance.repository.UserRepository;

@RestController
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    /*
     *   Create a user
     */
    @CrossOrigin
    @RequestMapping(path = "api/users", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody User input) {
        User resultUser = userRepository.save(new User(input.getLogin(), input.getEmail(), input.getPassword()));
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest().path("/{id}")
//                .buildAndExpand(resultUser.getId()).toUri();
//        return ResponseEntity.created(location).build();
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.set("Access-Control-Allow-Origin", environment.getProperty("allowedOrigin"));
        return new ResponseEntity<User>(resultUser, responseHeaders, HttpStatus.CREATED);
    }

    /*
     *   Get list of users
     */
    @CrossOrigin
    @RequestMapping(path = "api/users", method = RequestMethod.GET)
    public ResponseEntity<Iterable<User>> getUsers() {
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.set("Access-Control-Allow-Origin", environment.getProperty("allowedOrigin"));
        return new ResponseEntity<Iterable<User>>(userRepository.findAll(), responseHeaders, HttpStatus.OK);
    }

    /*
     * Get user by id
     */
    @CrossOrigin
    @RequestMapping(path = "api/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User searchedUser = userRepository.findOne(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.set("Access-Control-Allow-Origin", environment.getProperty("allowedOrigin"));
        if (searchedUser != null) {
            return new ResponseEntity<User>(searchedUser, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(null, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Update user with id
     */
    @CrossOrigin
    @RequestMapping(path = "api/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User input) {
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.set("Access-Control-Allow-Origin", environment.getProperty("allowedOrigin"));
        if (!id.equals(input.getId())) {
            return new ResponseEntity<User>(input, null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<User>(userRepository.save(input), responseHeaders, HttpStatus.OK);
        }
    }

    /*
     * Delete user with id
     */
    @CrossOrigin
    @RequestMapping(path = "api/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeUser(@PathVariable Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.set("Access-Control-Allow-Origin", environment.getProperty("allowedOrigin"));
        if(userRepository.exists(id)) {
            userRepository.delete(id);
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Check if login is available for user
     */
    @CrossOrigin
    @RequestMapping(path = "api/users/{login}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkIfLoginAvailable(@PathVariable String login) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (login != null) {
            if (userRepository.findByLogin(login).size() > 0) {
                return new ResponseEntity<Boolean>(false, responseHeaders, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(true, responseHeaders, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Boolean>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Check if email is available for user
     */
    @CrossOrigin
    @RequestMapping(path = "api/users/{email}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkIfEmailAvailable(@PathVariable String email) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (email != null) {
            if (userRepository.findByEmail(email).size() > 0) {
                return new ResponseEntity<Boolean>(false, responseHeaders, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(true, responseHeaders, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Boolean>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Check if password is correct for user in change password action
     */
    @CrossOrigin
    @RequestMapping(path = "api/user/checkPassword/{id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkPassword(@PathVariable Long id, @RequestBody String password) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (password != null) {
            if (userRepository.findOne(id).getPassword().equals(password)) {
                return new ResponseEntity<Boolean>(true, responseHeaders, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(false, responseHeaders, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Boolean>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }
}
