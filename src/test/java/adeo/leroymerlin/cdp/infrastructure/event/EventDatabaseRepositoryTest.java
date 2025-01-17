package adeo.leroymerlin.cdp.infrastructure.event;

import adeo.leroymerlin.cdp.application.FixtureEvent;
import adeo.leroymerlin.cdp.domain.event.EventVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
class EventDatabaseRepositoryTest {

    @InjectMocks
    private EventDatabaseRepository eventDatabaseRepository;
    @Mock
    private EventJpaRepository eventJpaRepository;

    @Test
    void shouldFindEventById() {
        // Given
        Mockito.when(eventJpaRepository.findById(1000L)).thenReturn(Optional.of(FixtureEvent.createEventEntity(1000L)));

        // When
        Optional<EventVO> eventById = eventDatabaseRepository.findEventById(1000L);

        // Then
        Assertions.assertThat(eventById).isPresent();
        Mockito.verify(eventJpaRepository, Mockito.times(1)).findById(1000L);
    }

    @Test
    void shouldReturnOptionalEmpty_whenNotFoundEvent() {
        // Given
        Mockito.when(eventJpaRepository.findById(1000L)).thenReturn(Optional.empty());

        // When
        Optional<EventVO> eventById = eventDatabaseRepository.findEventById(1000L);

        // Then
        Assertions.assertThat(eventById).isEmpty();
        Mockito.verify(eventJpaRepository, Mockito.times(1)).findById(1000L);
    }

    @Test
    void shouldSaveEvent() {
        // Given
        EventVO event = FixtureEvent.createEventVo(1000L, "comment", 5);

        Event eventEntity = FixtureEvent.createEventEntity(1000L);

        ArgumentCaptor<Event> eventSaveArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        Mockito.when(eventJpaRepository.save(isA(Event.class))).thenReturn(eventEntity);

        // When
        eventDatabaseRepository.save(event);

        // Then
        Mockito.verify(eventJpaRepository, Mockito.times(1)).save(eventSaveArgumentCaptor.capture());
        Event capturedUpdateEventArgument = eventSaveArgumentCaptor.getValue();

        Assertions.assertThat(event.id()).isEqualTo(capturedUpdateEventArgument.getId());
        Assertions.assertThat(event.comment()).isEqualTo(capturedUpdateEventArgument.getComment());
        Assertions.assertThat(event.nbStars()).isEqualTo(capturedUpdateEventArgument.getNbStars());
        Assertions.assertThat(event.title()).isEqualTo(capturedUpdateEventArgument.getTitle());
        Assertions.assertThat(event.bands()).hasSize(capturedUpdateEventArgument.getBands().size());
    }

    @Test
    void shouldDeleteEvent() {
        // Given
        ArgumentCaptor<Long> deleteEventIdCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.doNothing().when(eventJpaRepository).deleteById(1000L);

        // When
        eventDatabaseRepository.deleteById(1000L);

        // Then
        Mockito.verify(eventJpaRepository, Mockito.times(1)).deleteById(deleteEventIdCaptor.capture());
        Long capturedEventId = deleteEventIdCaptor.getValue();

        Assertions.assertThat(capturedEventId).isEqualTo(1000);
    }
}