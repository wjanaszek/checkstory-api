package org.wjanaszek.checkstory.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.wjanaszek.checkstory.persistance.model.Photo;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("select distinct p from Photo p where p.owner.id = :userId and p.story.id = :storyNumber order by p.createDate DESC")
    List<Photo> findAllBelongingToUserByUserId(@Param("userId") Long userId, @Param("storyNumber") Long storyNumber);
}
