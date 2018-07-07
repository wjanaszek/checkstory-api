package org.wjanaszek.checkstory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wjanaszek.checkstory.domain.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
