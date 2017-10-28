package org.wjanaszek.checkstory.persistance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Column(name = "photo_number", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_number_gen")
    @SequenceGenerator(name = "photo_number_gen", sequenceName = "photo_number_seq", allocationSize = 1)
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createDate;

    @Column(name = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date updateDate;

    @Column(name = "image_type", nullable = false)
    private String imageType;

    public Photo() {
    }

    public Photo(Long id, Story story, User owner, Character originalPhoto, String pathToFile, Date createDate, Date updateDate, String imageType) {
        this.id = id;
        this.story = story;
        this.owner = owner;
        this.originalPhoto = originalPhoto;
        this.pathToFile = pathToFile;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.imageType = imageType;
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

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
