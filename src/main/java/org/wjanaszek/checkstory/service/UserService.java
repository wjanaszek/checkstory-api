package org.wjanaszek.checkstory.service;

import org.wjanaszek.checkstory.domain.User;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;

import java.util.List;

public interface UserService {
    User findById(Long id) throws NoResourceFoundException;
    User findByUsername(String username);
    List<User> findAll ();
}
