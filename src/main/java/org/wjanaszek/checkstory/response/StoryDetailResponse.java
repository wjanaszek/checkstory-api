package org.wjanaszek.checkstory.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.wjanaszek.checkstory.domain.PhotoWithContent;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryDetailResponse {
    private List<PhotoWithContent> photos;
}
