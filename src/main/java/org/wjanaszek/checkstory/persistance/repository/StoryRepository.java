package org.wjanaszek.checkstory.persistance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wjanaszek.checkstory.persistance.model.Story;

import java.util.List;

public interface StoryRepository extends CrudRepository<Story, Long> {

//    @Query("select distinct s from Story s join User u on s.owner = u where u.id = :userId")
//    List<Story> findAllByUserId(@Param("userId") Long userId);
}
