package org.wjanaszek.checkstory.persistance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@IdClass(StoryPK.class)
@Table(name = "story", schema = "public")
public class Story implements Serializable {

//    @EmbeddedId
//    private StoryId id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "story_number")
    private Long storyNumber;

    @Id
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String title;
    private String notes;

    @Column(name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createDate;

    @OneToMany(mappedBy = "id.story", fetch = FetchType.LAZY)
    private Set<Photo> photos = new HashSet<>();

    protected Story() {}

    public Story(String title, String notes, Date createDate) {
        this.title = title;
        this.notes = notes;
        this.createDate = createDate;
    }

    public Long getStoryNumber() {
        return storyNumber;
    }

    public void setStoryNumber(Long storyNumber) {
        this.storyNumber = storyNumber;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    //    public StoryId getId() {
//        return id;
//    }
//
//    public void setId(StoryId id) {
//        this.id = id;
//    }

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

    public Set<Photo> getPhotos() { return this.photos; }

    public void setPhotos(Set<Photo> photos) { this.photos = photos; }
}
