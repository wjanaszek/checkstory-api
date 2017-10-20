package org.wjanaszek.checkstory.services;

import org.wjanaszek.checkstory.model.User;

public interface UserService {
    Iterable<User> listAllUsers();
    User getUserById(Long id);
    User saveUser(User user);
    void deleteUser(Long id);
}
