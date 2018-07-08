package org.wjanaszek.checkstory.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CreateUpdateStoryRequest {
    private String title;
    private String notes;
    private Double longitude;
    private Double latitude;
    private Date createDate;
}
