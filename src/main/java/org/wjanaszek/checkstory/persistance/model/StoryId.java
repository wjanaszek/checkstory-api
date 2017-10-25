package org.wjanaszek.checkstory.persistance.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class StoryId implements Serializable {

    @Column(name = "story_number")
    private Long storyNumber;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public StoryId() {
    }

    public StoryId(Long storyNumber, User owner) {
        this.storyNumber = storyNumber;
        this.owner = owner;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoryId storyId = (StoryId) o;

        if (storyNumber != null ? !storyNumber.equals(storyId.storyNumber) : storyId.storyNumber != null) return false;
        return owner != null ? owner.equals(storyId.owner) : storyId.owner == null;
    }

    @Override
    public int hashCode() {
        int result = storyNumber != null ? storyNumber.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
