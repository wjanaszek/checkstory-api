package org.wjanaszek.checkstory.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wjanaszek.checkstory.domain.User;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.repository.UserRepository;
import org.wjanaszek.checkstory.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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

}
