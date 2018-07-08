package org.wjanaszek.checkstory.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CreateUpdatePhotoRequest {

    private Long id;
    private String content;
    private Date createDate;
    private String originalPhoto;
    private Long storyId;
    private String imageType;

}
