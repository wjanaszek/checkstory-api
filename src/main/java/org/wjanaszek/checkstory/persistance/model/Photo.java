package org.wjanaszek.checkstory.persistance.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "photo", schema = "public")
public class Photo {

    @EmbeddedId
    private PhotoId id;

    @Column
    private char original;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "path_to_file")
    private String pathToFile;

    public Photo() {
    }

    public Photo(PhotoId id, char original, Date createDate, Date updateDate, String pathToFile) {
        this.id = id;
        this.original = original;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.pathToFile = pathToFile;
    }

    public PhotoId getId() {
        return id;
    }

    public void setId(PhotoId id) {
        this.id = id;
    }

    public char getOriginal() {
        return original;
    }

    public void setOriginal(char original) {
        this.original = original;
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

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }
}
