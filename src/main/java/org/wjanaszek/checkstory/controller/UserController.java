package org.wjanaszek.checkstory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.domain.User;
import org.wjanaszek.checkstory.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User logIn(@RequestBody User user) {

    }

}
