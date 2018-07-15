package org.wjanaszek.checkstory.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class StoryDetailWithPhotosResponse extends StoryDetailResponse {

    private Long id;
    private String title;
    private String notes;
    private Double latitude;
    private Double longitude;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pl_PL", timezone = "Europe/Warsaw")
    private Date createDate;

}
