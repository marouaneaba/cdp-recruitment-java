package adeo.leroymerlin.cdp.usecases;

import adeo.leroymerlin.cdp.application.FixtureEvent;
import adeo.leroymerlin.cdp.domain.BandVO;
import adeo.leroymerlin.cdp.domain.MemberVO;
import adeo.leroymerlin.cdp.domain.event.EventRepository;
import adeo.leroymerlin.cdp.domain.event.EventVO;
import adeo.leroymerlin.cdp.domain.event.exception.EventDetailsInvalidException;
import adeo.leroymerlin.cdp.domain.event.exception.EventNotFoundException;
import adeo.leroymerlin.cdp.domain.event.exception.QueryForEventSearchException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;


    @Test
    void shouldDeleteEvent() {
        // Given
        ArgumentCaptor<Long> deleteEventCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.doNothing().when(eventRepository).deleteById(any());

        // When
        eventService.delete(999L);

        // Then
        verify(eventRepository, times(1)).deleteById(deleteEventCaptor.capture());
        Assertions.assertThat(deleteEventCaptor.getValue()).isEqualTo(999);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenDeleteEventWithEventIdNull() {
        // Given

        // When
        ThrowableAssert.ThrowingCallable deleteEventThrowable = () -> eventService.delete(null);

        // Then
        verify(eventRepository, never()).deleteById(isA(Long.class));

        assertThatThrownBy(deleteEventThrowable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Event ID cannot be null");
    }

    @Test
    void shouldUpdateEventCommentAndNbStars() throws EventNotFoundException, EventDetailsInvalidException {
        // Given
        EventVO eventVo = FixtureEvent.createEventVo(1000L, "new comment", 5);

        ArgumentCaptor<Long> findEventByIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<EventVO> saveEventArgumentCaptor = ArgumentCaptor.forClass(EventVO.class);

        Mockito.when(eventRepository.findEventById(1000L)).thenReturn(Optional.of(eventVo));
        Mockito.doNothing().when(eventRepository).save(eventVo);

        // When
        eventService.updateEventWithCommentAndStars(1000L, eventVo);

        // Then
        verify(eventRepository, times(1)).findEventById(findEventByIdArgumentCaptor.capture());
        verify(eventRepository, times(1)).save(saveEventArgumentCaptor.capture());

        Assertions.assertThat(findEventByIdArgumentCaptor.getValue()).isEqualTo(1000L);
        Assertions.assertThat(saveEventArgumentCaptor.getValue().nbStars()).isEqualTo(5);
        Assertions.assertThat(saveEventArgumentCaptor.getValue().comment()).isEqualTo("new comment");
    }

    @Test
    void shouldThrowEventDetailsInvalidException_whenUpdateEventAndEventVoOrEventIdIsNull() {
        // Given
        EventVO eventVo = FixtureEvent.createEventVo(1000L, "new comment", 5);

        // When
        ThrowableAssert.ThrowingCallable updateEventThrowable = () -> eventService.updateEventWithCommentAndStars(null, eventVo);

        // Then
        verify(eventRepository, never()).findEventById(isA(Long.class));
        verify(eventRepository, never()).save(isA(EventVO.class));

        assertThatThrownBy(updateEventThrowable)
                .isInstanceOf(EventDetailsInvalidException.class)
                .hasMessageContaining("Event ID and Event details cannot be null");
    }

    @Test
    void shouldThrowEventDetailsInvalidException_whenUpdateEventAndNbStarsIsLessThanZero() {
        // Given
        EventVO eventVo = FixtureEvent.createEventVo(1000L, "new comment", -1);

        // When
        ThrowableAssert.ThrowingCallable updateEventThrowable = () -> eventService.updateEventWithCommentAndStars(1000L, eventVo);

        // Then
        verify(eventRepository, never()).findEventById(isA(Long.class));
        verify(eventRepository, never()).save(isA(EventVO.class));

        assertThatThrownBy(updateEventThrowable)
                .isInstanceOf(EventDetailsInvalidException.class)
                .hasMessageContaining("Number of stars must be between 0 and 5");
    }

    @Test
    void shouldThrowEventDetailsInvalidException_whenUpdateEventAndNbStarsIsGreaterThanFive() {
        // Given
        EventVO eventVo = FixtureEvent.createEventVo(1000L, "new comment", 6);

        // When
        ThrowableAssert.ThrowingCallable updateEventThrowable = () -> eventService.updateEventWithCommentAndStars(1000L, eventVo);

        // Then
        verify(eventRepository, never()).findEventById(isA(Long.class));
        verify(eventRepository, never()).save(isA(EventVO.class));

        assertThatThrownBy(updateEventThrowable)
                .isInstanceOf(EventDetailsInvalidException.class)
                .hasMessageContaining("Number of stars must be between 0 and 5");
    }

    @Test
    void shouldThrowEventNotFoundException_whenUpdateEventAndEventIdNotFound() {
        // Given
        EventVO eventVo = FixtureEvent.createEventVo(1000L, "new comment", 5);

        ArgumentCaptor<Long> findEventByIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.when(eventRepository.findEventById(1000L)).thenReturn(Optional.empty());

        // When
        ThrowableAssert.ThrowingCallable updateEventThrowable = () -> eventService.updateEventWithCommentAndStars(1000L, eventVo);

        // Then
        assertThatThrownBy(updateEventThrowable)
                .isInstanceOf(EventNotFoundException.class)
                .hasMessageContaining("Event 1000 not found");

        verify(eventRepository, times(1)).findEventById(findEventByIdArgumentCaptor.capture());
        verify(eventRepository, times(0)).save(any());

        Assertions.assertThat(findEventByIdArgumentCaptor.getValue()).isEqualTo(1000L);
    }

    @Test
    void shouldReturnEvent_whenSearchEventsByMemberNameAndEventHasMember() throws QueryForEventSearchException {
        // Given
        Mockito.when(eventRepository.findAll()).thenReturn(List.of(FixtureEvent.createEventVo(1000L, "new comment", 5)));

        // When
        List<EventVO> eventHasMember = eventService.searchEventsByMemberName("wa");

        // Then
        Assertions.assertThat(eventHasMember).hasSize(1);
        Assertions.assertThat(eventHasMember)
                .extracting(EventVO::title)
                .containsExactly("Concert Rock Night [1]");

        Assertions.assertThat(eventHasMember.get(0).bands())
                .extracting(BandVO::name)
                .containsExactly("The Shiny Stars [1]");

        Assertions.assertThat(eventHasMember.get(0).bands())
                .flatExtracting(BandVO::members)
                .extracting(MemberVO::name)
                .contains("Queen Anika Walsh")
                .doesNotContain("Ringo Starr", "John Lennon")
                .hasSize(1);
    }

    @Test
    void shouldReturnEventEmptyEventList_whenSearchEventsByMemberNameAndMemberNameNotFoundInAnyEvent() throws QueryForEventSearchException {
        // Given
        Mockito.when(eventRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<EventVO> eventHasMember = eventService.searchEventsByMemberName("wa");

        // Then
        Assertions.assertThat(eventHasMember).isEmpty();
    }

    @Test
    void shouldReturnEventTitleAndBandNameWithCount_whenSearchEventsByMemberNameAndEventHasMember() throws QueryForEventSearchException {
        // Given
        Mockito.when(eventRepository.findAll()).thenReturn(List.of(FixtureEvent.createEventVo(1000L, "new comment", 5)));

        // When
        List<EventVO> eventHasMember = eventService.searchEventsByMemberName("Jagger");

        // Then
        Assertions.assertThat(eventHasMember).hasSize(1);
        Assertions.assertThat(eventHasMember)
                .extracting(EventVO::title)
                .containsExactly("Concert Rock Night [1]");

        Assertions.assertThat(eventHasMember.get(0).bands())
                .extracting(BandVO::name)
                .containsExactly("The Shiny Stars [1]");

        Assertions.assertThat(eventHasMember.get(0).bands())
                .flatExtracting(BandVO::members)
                .extracting(MemberVO::name)
                .contains("Mick Jagger")
                .doesNotContain("Ringo Starr", "John Lennon")
                .hasSize(1);
    }

    @Test
    void shouldReturnEventTitleAndBandNameWithCount_whenSearchEventsByMemberNameAndManyEventHasMember() throws QueryForEventSearchException {
        // Given
        Mockito.when(eventRepository.findAll()).thenReturn(FixtureEvent.createEventVo());

        // When
        List<EventVO> eventHasMember = eventService.searchEventsByMemberName("Jagger");

        // Then
        Assertions.assertThat(eventHasMember).hasSize(2);
        Assertions.assertThat(eventHasMember)
                .extracting(EventVO::title)
                .containsExactlyInAnyOrder("Concert Rock Night [2]", "Jazz Night Extravaganza [2]");

        Assertions.assertThat(eventHasMember.get(0).bands())
                .extracting(BandVO::name)
                .containsExactlyInAnyOrder("The Shiny Stars [1]", "The Rolling Beats [1]");

        Assertions.assertThat(eventHasMember.get(0).bands())
                .flatExtracting(BandVO::members)
                .extracting(MemberVO::name)
                .contains("Mick Jagger", "Jagger Lennon")
                .doesNotContain("Ringo Starr", "John Lennon")
                .hasSize(2);
    }

    @Test
    void shouldReturnZeroEvent_whenSearchEventsByMemberNameAndMemberNameNotFoundInAnyEvent() throws QueryForEventSearchException {
        // Given
        Mockito.when(eventRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<EventVO> eventHasMember = eventService.searchEventsByMemberName("wa");

        // Then
        Assertions.assertThat(eventHasMember).isEmpty();
    }

    @Test
    void shouldThrowQueryForEventSearchException_whenSearchEventsByMemberNameAndQueryIsBlank() {
        // Given
        // When
        ThrowableAssert.ThrowingCallable searchEventsByMemberName = () -> eventService.searchEventsByMemberName(" ");

        // Then
        verify(eventRepository, never()).findAll();

        assertThatThrownBy(searchEventsByMemberName)
                .isInstanceOf(QueryForEventSearchException.class)
                .hasMessageContaining("Cannot search for events with an empty or null query.");
    }

}