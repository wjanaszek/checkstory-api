package org.wjanaszek.checkstory.repository;

import org.springframework.data.repository.CrudRepository;
import org.wjanaszek.checkstory.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByLogin(String login);
    User findByEmail(String email);
}
