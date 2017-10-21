package org.wjanaszek.checkstory.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wjanaszek.checkstory.persistance.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
