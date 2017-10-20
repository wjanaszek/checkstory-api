package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wjanaszek.checkstory.persistance.model.User;
import org.wjanaszek.checkstory.persistance.repository.UserRepository;

@RestController
public class AuthenticateRestController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "api/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@RequestBody User input) {
        if (userRepository.exists(input.getId())) {
            User user = userRepository.findOne(input.getId());
            // check password
            if (input.getPassword().equals(user.getPassword())) {
                return new ResponseEntity<User>(user, null, HttpStatus.OK);
            } else {
                return new ResponseEntity<User>(user, null, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<User>(null, null, HttpStatus.UNAUTHORIZED);
        }
    }
}
