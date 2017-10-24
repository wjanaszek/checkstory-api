package org.wjanaszek.checkstory.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wjanaszek.checkstory.persistance.model.Story;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {

    @Query("select distinct s from Story s where s.owner.id = :userId")
    List<Story> findAllBelongingToUserByUserId(@Param("userId") Long userId);
}
