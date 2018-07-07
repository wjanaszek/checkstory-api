package org.wjanaszek.checkstory.response;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class ImagesCompareResultResponse {
    private String content;
    private String imageType;
}
