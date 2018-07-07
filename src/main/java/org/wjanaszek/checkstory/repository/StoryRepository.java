package org.wjanaszek.checkstory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wjanaszek.checkstory.domain.Story;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {
    List<Story> findByOwnerId(Long ownerId);
}
