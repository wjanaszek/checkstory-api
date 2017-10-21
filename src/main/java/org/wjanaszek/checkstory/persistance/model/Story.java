package org.wjanaszek.checkstory.persistance.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "story", schema = "public")
public class Story implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private Double latitude;
    private Double longitude;

    @Column(name = "start_date")
    private Date startDate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "story", fetch = FetchType.LAZY)
    private Set<Photo> photos;

    protected Story() {}

    public Story(String description, Double latitude, Double longitude, Date startDate, User owner) {
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Double getLatitude() { return this.latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return this.longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

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

    public Set<Photo> getPhotos() { return this.photos; }

    public void setPhotos(Set<Photo> photos) { this.photos = photos; }
}
