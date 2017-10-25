package org.wjanaszek.checkstory.persistance.model;

import java.io.Serializable;

public class StoryPK implements Serializable {
    Long storyNumber;
    User owner;

    public StoryPK() {
    }

    public StoryPK(Long storyNumber, User owner) {
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

        StoryPK storyPK = (StoryPK) o;

        if (storyNumber != null ? !storyNumber.equals(storyPK.storyNumber) : storyPK.storyNumber != null) return false;
        return owner != null ? owner.equals(storyPK.owner) : storyPK.owner == null;
    }

    @Override
    public int hashCode() {
        int result = storyNumber != null ? storyNumber.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
