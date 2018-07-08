package org.wjanaszek.checkstory.service;

import org.wjanaszek.checkstory.domain.User;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.request.CreateAdminRequest;
import org.wjanaszek.checkstory.request.CreateUserRequest;
import org.wjanaszek.checkstory.request.UpdateAdminRequest;

import java.util.List;

public interface UserService {
    User findById(Long id) throws NoResourceFoundException;

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findAll();

    void createAdmin(CreateAdminRequest createAdminRequest);

    void createUser(CreateUserRequest createUserRequest);

    void disableUser(Long id);

    void enableUser(Long id);

    void removeUser(Long id);

    User updateAdmin(Long id, UpdateAdminRequest updateAdminRequest);
}
