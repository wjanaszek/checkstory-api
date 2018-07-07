package org.wjanaszek.checkstory.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateStoryRequest {
    private String title;
    private String notes;
    private Double longitude;
    private Double latitude;
}
