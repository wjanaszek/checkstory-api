package org.wjanaszek.checkstory.persistance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "photo", schema = "public")
public class Photo {

    @Id
    @Column(name = "photo_no")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "story_id")
    @JsonBackReference
    private Story story;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    protected Photo() {}
}
