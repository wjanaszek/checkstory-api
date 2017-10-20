package org.wjanaszek.checkstory.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wjanaszek.checkstory.persistance.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
    User findByEmail(String email);
}
