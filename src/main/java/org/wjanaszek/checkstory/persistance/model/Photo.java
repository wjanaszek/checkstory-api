package org.wjanaszek.checkstory.persistance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "photo", schema = "public")
public class Photo {

    /****************** begin of PK **********************************/
    /*
     * PK part
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_number")
    private Long id;

    /*
     * PK part
     */
    @ManyToOne
    @JoinColumn(name = "story_number", nullable = false)
    @JsonBackReference
    private Story story;

    /*
     * PK part
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore
    private User owner;
    /****************** end of PK **********************************/

    @Column(name = "original_photo", nullable = false)
    private Character originalPhoto;

    @Column(name = "path_to_file", nullable = false)
    private String pathToFile;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    public Photo() {
    }

    public Photo(Long id, Story story, User owner, Character originalPhoto, String pathToFile, Date createDate, Date updateDate) {
        this.id = id;
        this.story = story;
        this.owner = owner;
        this.originalPhoto = originalPhoto;
        this.pathToFile = pathToFile;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Character getOriginalPhoto() {
        return originalPhoto;
    }

    public void setOriginalPhoto(Character originalPhoto) {
        this.originalPhoto = originalPhoto;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
