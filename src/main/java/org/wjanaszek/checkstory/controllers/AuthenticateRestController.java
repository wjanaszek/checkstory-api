package org.wjanaszek.checkstory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
     * Log in user
     */
    @CrossOrigin
    @RequestMapping(path = "api/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationBodyRequest body) {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userRepository.findByLogin(body.getLogin());
        System.out.println("password = " + body.getPassword());
        System.out.println("encode = " + bCryptPasswordEncoder.encode(body.getPassword()));
        System.out.println("user.password = " + user.getPassword());
        System.out.println(user.getPassword().equals(bCryptPasswordEncoder.encode(body.getPassword())));
        if (user != null) {
            AuthenticationResponse responseBody = new AuthenticationResponse(user, "fake-jwt-token");
            return new ResponseEntity<AuthenticationResponse>(responseBody, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.UNAUTHORIZED);
        }
    }
}
