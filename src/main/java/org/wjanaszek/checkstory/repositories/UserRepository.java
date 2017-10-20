package org.wjanaszek.checkstory.repositories;

import org.springframework.data.repository.CrudRepository;
import org.wjanaszek.checkstory.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
