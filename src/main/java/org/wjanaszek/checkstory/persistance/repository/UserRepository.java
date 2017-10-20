package org.wjanaszek.checkstory.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.wjanaszek.checkstory.persistance.model.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByLogin(String login);
    List<User> findByEmail(String email);
}
