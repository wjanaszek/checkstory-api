package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.persistance.model.User;
import org.wjanaszek.checkstory.persistance.repository.UserRepository;
import org.wjanaszek.checkstory.utils.AuthenticationBodyRequest;
import org.wjanaszek.checkstory.utils.AuthenticationResponse;

@RestController
public class AuthenticateRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    /*
     * Log in user
     */
    @CrossOrigin
    @RequestMapping(path = "api/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationBodyRequest body) {
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.set("Access-Control-Allow-Origin", environment.getProperty("allowedOrigin"));
        User user = userRepository.findByLogin(body.getLogin()).get(0);
        if (user.getPassword().equals(body.getPassword())) {
            AuthenticationResponse responseBody = new AuthenticationResponse(user, "fake-jwt-token");
            return new ResponseEntity<AuthenticationResponse>(responseBody, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
        }
    }
}
