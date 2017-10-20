package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.persistance.model.User;
import org.wjanaszek.checkstory.persistance.repository.UserRepository;
import org.wjanaszek.checkstory.utils.AuthenticateResponse;

@RestController
public class AuthenticateRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    @CrossOrigin
    @RequestMapping(path = "api/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@RequestBody User input) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", environment.getProperty("allowedOrigin"));
        if (userRepository.exists(input.getId())) {
            User user = userRepository.findOne(input.getId());
            // check password
            if (input.getPassword().equals(user.getPassword())) {
                return new ResponseEntity<AuthenticateResponse>(
                        new AuthenticateResponse(user.getId(), user.getLogin(), "fake-jwt-token"),
                        responseHeaders,
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<User>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
        }
    }
}
