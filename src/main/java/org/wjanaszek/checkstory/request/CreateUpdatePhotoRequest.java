package org.wjanaszek.checkstory.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUpdatePhotoRequest {
    private Long id;
    private String content;
    private String createDate;
    private String originalPhoto;
    private Long storyId;
    private String imageType;

}
