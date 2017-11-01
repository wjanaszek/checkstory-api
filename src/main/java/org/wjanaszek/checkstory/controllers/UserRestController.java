package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.persistance.model.User;
import org.wjanaszek.checkstory.persistance.repository.UserRepository;
import org.wjanaszek.checkstory.utils.AuthenticationFacade;

@RestController
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    /*
     *   Create a user
     */
    @RequestMapping(path = "api/users", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody User input) {
        User resultUser = userRepository.save(new User(input.getLogin(), input.getEmail(), bCryptPasswordEncoder.encode(input.getPassword())));
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<User>(resultUser, responseHeaders, HttpStatus.CREATED);
    }

    /*
     *   Get list of users (Admin use only)
     */
    @RequestMapping(path = "api/users", method = RequestMethod.GET)
    public ResponseEntity<Iterable<User>> getUsers() {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<Iterable<User>>(userRepository.findAll(), responseHeaders, HttpStatus.OK);
    }

    /*
     * Get user by id
     */
    @RequestMapping(path = "api/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User searchedUser = userRepository.findOne(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (searchedUser != null && authenticationFacade.getAuthentication().getName().equals(searchedUser.getLogin())) {
            return new ResponseEntity<User>(searchedUser, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(null, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Update user with id
     */
    @RequestMapping(path = "api/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User input) {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        if (user != null && id.equals(input.getId()) && user.getId() == id) {
            return new ResponseEntity<User>(userRepository.save(input), responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Delete user with id
     */
    @RequestMapping(path = "api/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeUser(@PathVariable Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        User userToDelete = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        if(userToDelete != null && userToDelete.getId() == id) {
            userRepository.delete(id);
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Check if login is available for user
     */
    @RequestMapping(path = "api/users/checkLogin", method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkIfLoginAvailable(@RequestBody String login) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (login != null) {
            if (userRepository.findByLogin(login) != null) {
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
    @RequestMapping(path = "api/users/checkEmail", method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkIfEmailAvailable(@RequestBody String email) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (email != null) {
            if (userRepository.findByEmail(email) != null) {
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
    @RequestMapping(path = "api/users/checkPassword/{id}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkPassword(@PathVariable Long id, @RequestBody String password) {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
        if (user != null && password != null && user.getId() == id) {
            if (user.getPassword().equals(bCryptPasswordEncoder.encode(password))) {
                return new ResponseEntity<Boolean>(true, responseHeaders, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(false, responseHeaders, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Boolean>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }
}
