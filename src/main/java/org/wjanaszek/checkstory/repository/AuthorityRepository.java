package org.wjanaszek.checkstory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wjanaszek.checkstory.domain.Authority;
import org.wjanaszek.checkstory.domain.UserRoleName;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(UserRoleName name);
}
