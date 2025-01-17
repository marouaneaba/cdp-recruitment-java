package adeo.leroymerlin.cdp.domain.event;


import adeo.leroymerlin.cdp.domain.event.exception.EventDetailsInvalidException;
import adeo.leroymerlin.cdp.domain.event.exception.EventNotFoundException;
import adeo.leroymerlin.cdp.domain.event.exception.QueryForEventSearchException;

import java.util.List;

public interface EventManagementPort {

    List<EventVO> getEvents();

    void delete(Long eventId);

    List<EventVO> searchEventsByMemberName(String query) throws QueryForEventSearchException;

    void updateEventWithCommentAndStars(Long eventId, EventVO eventVO) throws EventNotFoundException, EventDetailsInvalidException;
}
