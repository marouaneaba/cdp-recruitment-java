package adeo.leroymerlin.cdp.infrastructure.event;

import adeo.leroymerlin.cdp.domain.event.EventVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class EventDatabaseRepositoryIT {


    private EventDatabaseRepository eventDatabaseRepository;
    @Autowired
    private EventJpaRepository eventJpaRepository;


    @BeforeEach
    void setUp() {
        eventDatabaseRepository = new EventDatabaseRepository(eventJpaRepository);
    }

    @Test
    void shouldFindEventById() {
        // Given
        // When
        Optional<EventVO> eventById = eventDatabaseRepository.findEventById(1000L);

        // Then
        Assertions.assertThat(eventById).isPresent();
        Assertions.assertThat(eventById.get().id()).isEqualTo(1000L);
    }

    @Test
    void shouldReturnOptionalEmpty_whenNotFoundEvent() {
        // Given
        // When
        Optional<EventVO> eventById = eventDatabaseRepository.findEventById(99999L);

        // Then
        Assertions.assertThat(eventById).isEmpty();
    }

    @Test
    void shouldSaveEvent() {
        // Given
        Optional<EventVO> existingEvent = eventDatabaseRepository.findEventById(1000L);
        Optional<EventVO> eventUpdated = existingEvent.map(event -> event.withNbStars(5).withComment("comment"));

        // When
        eventDatabaseRepository.save(eventUpdated.get());

        // Then
        Optional<EventVO> eventById = eventDatabaseRepository.findEventById(1000L);
        Assertions.assertThat(eventById).isPresent();
        Assertions.assertThat(eventById.get().id()).isEqualTo(1000L);
        Assertions.assertThat(eventById.get().nbStars()).isEqualTo(5);
        Assertions.assertThat(eventById.get().comment()).isEqualTo("comment");
    }

    @Test
    void shoulDeleteEvent() {
        // Given

        // When
        eventDatabaseRepository.deleteById(1000L);

        // Then
        Optional<EventVO> eventById = eventDatabaseRepository.findEventById(1000L);
        Assertions.assertThat(eventById).isEmpty();
    }
}