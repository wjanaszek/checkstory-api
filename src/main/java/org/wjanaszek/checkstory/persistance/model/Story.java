package org.wjanaszek.checkstory.persistance.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "story", schema = "public")
public class Story implements Serializable {

    @EmbeddedId
    private StoryId id;

    private String title;
    private String notes;

    @Column(name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "story", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Photo> photos = new HashSet<>();

    protected Story() {}

    public Story(String title, String notes, Date createDate, User owner) {
        this.title = title;
        this.notes = notes;
        this.createDate = createDate;
        this.owner = owner;
    }

    public StoryId getId() {
        return id;
    }

    public void setId(StoryId id) {
        this.id = id;
    }

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Photo> getPhotos() { return this.photos; }

    public void setPhotos(Set<Photo> photos) { this.photos = photos; }
}
