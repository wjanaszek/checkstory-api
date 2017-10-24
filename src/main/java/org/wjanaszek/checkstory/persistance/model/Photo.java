package org.wjanaszek.checkstory.persistance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "photo", schema = "public")
public class Photo {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "story_id")
    @JsonBackReference
    private Story story;

    @Column(name = "create_date")
    private Date createDate;

    protected Photo() {}
}
