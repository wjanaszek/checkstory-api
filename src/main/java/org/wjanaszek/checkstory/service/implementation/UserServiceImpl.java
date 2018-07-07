package org.wjanaszek.checkstory.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.wjanaszek.checkstory.domain.Authority;
import org.wjanaszek.checkstory.domain.User;
import org.wjanaszek.checkstory.domain.UserRoleName;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.repository.AuthorityRepository;
import org.wjanaszek.checkstory.repository.UserRepository;
import org.wjanaszek.checkstory.request.CreateAdminRequest;
import org.wjanaszek.checkstory.request.CreateUserRequest;
import org.wjanaszek.checkstory.request.UpdateAdminRequest;
import org.wjanaszek.checkstory.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) throws AccessDeniedException, NoResourceFoundException {
        return Optional.of(userRepository.findOne(id)).orElseThrow(NoResourceFoundException::new);
    }

    public List<User> findAll() throws AccessDeniedException {
        return userRepository.findAll();
    }

    public void createAdmin(CreateAdminRequest createAdminRequest) {
        User newUser = this.getUserFromRequest(createAdminRequest);

        Authority authority = authorityRepository.findByName(UserRoleName.ROLE_ADMIN);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
    }

    public void createUser(CreateUserRequest createUserRequest) {
        User newUser = this.getUserFromRequest(createUserRequest);

        Authority authority = authorityRepository.findByName(UserRoleName.ROLE_USER);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
    }

    public void disableUser(Long id) {
        User user = userRepository.findOne(id);
        if (user != null) {
            user.setEnabled(false);
            userRepository.save(user);
        }
    }

    public void enableUser(Long id) {
        User user = userRepository.findOne(id);
        if (user != null) {
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    public void removeUser(Long id) {
        userRepository.delete(id);
    }

    public User updateAdmin(Long id, UpdateAdminRequest updateAdminRequest) {
        return null;
    }

    private User getUserFromRequest(CreateUserRequest createUserRequest) {
        User newUser = new User();
        newUser.setUsername(createUserRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        newUser.setEmail(createUserRequest.getEmail());
        newUser.setEnabled(true);
        newUser.setAdmin(createUserRequest instanceof CreateAdminRequest);
        return newUser;
    }
}
