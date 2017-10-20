package org.wjanaszek.checkstory.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.wjanaszek.checkstory.persistance.model.Story;

public interface StoryRepository extends CrudRepository<Story, Long> {

}
