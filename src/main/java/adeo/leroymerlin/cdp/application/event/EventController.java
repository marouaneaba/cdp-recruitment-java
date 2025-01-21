package adeo.leroymerlin.cdp.application.event;

import adeo.leroymerlin.cdp.domain.event.EventManagementPort;
import adeo.leroymerlin.cdp.domain.event.exception.EventDetailsInvalidException;
import adeo.leroymerlin.cdp.domain.event.exception.EventNotFoundException;
import adeo.leroymerlin.cdp.domain.event.exception.QueryForEventSearchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    private final EventManagementPort eventManagementPort;

    public EventController(EventManagementPort eventService) {
        this.eventManagementPort = eventService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EventDto> findEvents() {
        return this.eventManagementPort.getEvents()
                .stream().map(EventDtoMapper::toDto)
                .toList();
    }

    @GetMapping(value = "/search/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EventDto> findEvents(@PathVariable String query) throws QueryForEventSearchException {
        return this.eventManagementPort.searchEventsByMemberName(query)
                .stream().map(EventDtoMapper::toDto)
                .toList();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long id) {
        this.eventManagementPort.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto) throws EventNotFoundException, EventDetailsInvalidException {

        log.info("Updating event id: {}", id);
        this.eventManagementPort.updateEventWithCommentAndStars(id, EventDtoMapper.toVO(eventDto));
    }
}
