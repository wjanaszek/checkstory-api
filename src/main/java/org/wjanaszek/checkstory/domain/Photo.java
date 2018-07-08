package org.wjanaszek.checkstory.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "photos", schema = "checkstory")
@Getter
@Setter
@NoArgsConstructor
public class Photo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    @JsonIgnore
    private Story story;

    @Column(name = "path_to_file", nullable = false)
    private String pathToFile;

    @Column(name = "create_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pl_PL", timezone = "Europe/Warsaw")
    private Date createDate;

    @Column(name = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pl_PL", timezone = "Europe/Warsaw")
    private Date updateDate;

    @Column(name = "image_type", nullable = false)
    private String imageType;

    @Column(name = "original_photo", nullable = false)
    private Character originalPhoto;

}
