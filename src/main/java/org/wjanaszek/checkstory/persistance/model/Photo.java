package org.wjanaszek.checkstory.persistance.model;

import javax.persistence.*;

@Entity
@Table(name = "photo", schema = "public")
public class Photo {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

    protected Photo() {}
}
