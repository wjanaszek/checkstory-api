package org.wjanaszek.checkstory.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.wjanaszek.checkstory.domain.Story;
import org.wjanaszek.checkstory.exception.BadRequestException;
import org.wjanaszek.checkstory.request.CreateStoryRequest;
import org.wjanaszek.checkstory.service.StoryService;
import org.wjanaszek.checkstory.utils.TimeProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StoryServiceImpl implements StoryService {

    @Autowired
    private TimeProvider timeProvider;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Story createStoryEntity(CreateStoryRequest createStoryRequest) throws BadRequestException {
        if (createStoryRequest.getTitle() == null || createStoryRequest.getLatitude() == null || createStoryRequest.getLongitude() == null) {
            throw new BadRequestException();
        }

        Story story = new Story();
        try {
            story.setCreateDate(format.parse(timeProvider.now().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        story.setLatitude(createStoryRequest.getLatitude());
        story.setLongitude(createStoryRequest.getLongitude());
        story.setNotes(createStoryRequest.getNotes());
        story.setTitle(createStoryRequest.getTitle());
        return story;
    }

}
