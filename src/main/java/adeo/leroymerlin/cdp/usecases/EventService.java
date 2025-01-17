package adeo.leroymerlin.cdp.usecases;

import adeo.leroymerlin.cdp.domain.BandVO;
import adeo.leroymerlin.cdp.domain.MemberVO;
import adeo.leroymerlin.cdp.domain.event.EventManagementPort;
import adeo.leroymerlin.cdp.domain.event.EventRepository;
import adeo.leroymerlin.cdp.domain.event.EventVO;
import adeo.leroymerlin.cdp.domain.event.exception.EventDetailsInvalidException;
import adeo.leroymerlin.cdp.domain.event.exception.EventNotFoundException;
import adeo.leroymerlin.cdp.domain.event.exception.QueryForEventSearchException;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class EventService implements EventManagementPort {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional(readOnly = true)
    public List<EventVO> getEvents() {
        log.debug("Fetching all events.");
        return eventRepository.findAll();
    }

    @Transactional
    public void delete(Long eventId) {
        if (null == eventId) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }

        eventRepository.deleteById(eventId);
        log.info("Event with id: {} has been successfully deleted", eventId);
    }

    @Transactional(readOnly = true)
    public List<EventVO> searchEventsByMemberName(String memberNameQuery) throws QueryForEventSearchException {
        if (Strings.isBlank(memberNameQuery)) {
            log.warn("Empty or null query received for member name search.");
            throw new QueryForEventSearchException("Cannot search for events with an empty or null query.");
        }

        log.debug("Searching events containing member matching query: {}", memberNameQuery);
        return eventRepository.findAll().stream()
                .filter(event -> event.containsMemberMatchingName(memberNameQuery))
                .map(event -> eventWithBandCountInTitle(memberNameQuery, event))
                .toList();
    }

    private EventVO eventWithBandCountInTitle(String memberNameQuery, EventVO event) {
        Set<BandVO> filteredBands = filterBandsByQuery(event.bands(), memberNameQuery);
        String bandCountInTitle = String.format("%s [%s]", event.title(), filteredBands.size());
        return event.withBands(filteredBands).withTitle(bandCountInTitle);
    }

    private Set<BandVO> filterBandsByQuery(Set<BandVO> bands, String memberNameQuery) {
        return bands.stream()
                .filter(band -> band.containsMemberMatchingName(memberNameQuery))
                .map(band -> eventWithMemberCountInName(memberNameQuery, band))
                .collect(Collectors.toSet());
    }

    private BandVO eventWithMemberCountInName(String memberNameQuery, BandVO band) {
        Set<MemberVO> filteredMembers = filteredMembersByQuery(band.members(), memberNameQuery);
        String memberCountInName = String.format("%s [%s]", band.name(), filteredMembers.size());
        return band.withMembers(filteredMembers).withMembers(memberCountInName);
    }

    private Set<MemberVO> filteredMembersByQuery(Set<MemberVO> members, String query) {
        return members.stream()
                .filter(member -> member.containsName(query))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void updateEventWithCommentAndStars(Long eventId, EventVO eventDetails) throws EventNotFoundException, EventDetailsInvalidException {
        if (null == eventId || null == eventDetails) {
            throw new EventDetailsInvalidException("Event ID and Event details cannot be null");
        }

        if (!eventDetails.isNbStarsValid()) {
            throw new EventDetailsInvalidException("Number of stars must be between 0 and 5");
        }

        EventVO existingEvent = this.eventRepository.findEventById(eventId)
                .orElseThrow(() -> {
                    log.error("No event found with ID {}.", eventId);
                    return new EventNotFoundException(eventId);
                });

        EventVO eventUpdated = existingEvent
                .withComment(eventDetails.comment())
                .withNbStars(eventDetails.nbStars());

        this.eventRepository.save(eventUpdated);

        log.info("Event with ID {} has been updated successfully with new details", eventId);
    }
}
