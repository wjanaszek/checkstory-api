package org.wjanaszek.checkstory.persistance.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "story", schema = "public")
public class Story implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
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

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
