package org.wjanaszek.checkstory.persistance.model;

import javax.persistence.*;
import javax.xml.stream.events.DTD;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "story")
public class Story implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String description;
    private Date startDate;
    private User owner;
    // TODO maybe large binary objects? Or maybe another class with photo/story detail?
    // private Set<String> photosDetails;

    protected Story() {}

    public Story(String description, Date startDate, User owner) {
        this.description = description;
        this.startDate = startDate;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public User getOwner() {
        return this.owner;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id")
    public void setOwner(User owner) {
        this.owner = owner;
    }
}
