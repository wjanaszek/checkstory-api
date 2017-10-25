package org.wjanaszek.checkstory.persistance.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PhotoId implements Serializable {

    @Column(name = "photo_number")
    private Long photoNumber;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "story_number", referencedColumnName = "story_number"),
            @JoinColumn(name = "story_owner", referencedColumnName = "owner_id")
    })
    private Story story;

//    @ManyToOne
//    @JoinColumn(name = "story_owner")
//    private User owner;

    public PhotoId() {
    }

    public PhotoId(Long photoNumber, Story story) {
        this.photoNumber = photoNumber;
        this.story = story;
//        this.owner = owner;
    }

    public Long getPhotoNumber() {
        return photoNumber;
    }

    public void setPhotoNumber(Long photoNumber) {
        this.photoNumber = photoNumber;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

//    public User getOwner() {
//        return owner;
//    }
//
//    public void setOwner(User owner) {
//        this.owner = owner;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhotoId)) return false;
        PhotoId that = (PhotoId) o;
        return Objects.equals(((PhotoId) o).getPhotoNumber(), that.getPhotoNumber())
//                && Objects.equals(((PhotoId) o).getOwner(), that.getOwner())
                && Objects.equals(((PhotoId) o).getStory(), that.getStory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhotoNumber(), getStory());
    }
}
