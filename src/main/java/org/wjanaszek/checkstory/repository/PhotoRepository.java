package org.wjanaszek.checkstory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wjanaszek.checkstory.domain.Photo;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> getPhotosByStoryId(Long id);
}
