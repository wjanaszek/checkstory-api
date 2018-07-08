package org.wjanaszek.checkstory.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComparePhotosRequest {

    private String first;
    private String firstType;
    private String second;
    private String secondType;
    private Integer sensitivity;    // optional

}
