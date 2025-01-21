package adeo.leroymerlin.cdp.domain.event;


import adeo.leroymerlin.cdp.domain.BandVO;
import adeo.leroymerlin.cdp.domain.MemberVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class EventVOTest {


    @Test
    void shouldCreateNewEventWithUpdatedComment() {
        // Given
        EventVO event = new EventVO(1L, "Concert", "https://img.com", Set.of(), 5, "Great concert");

        // When
        EventVO updatedEvent = event.withComment("experience");

        // Then
        Assertions.assertThat(updatedEvent.comment()).isEqualTo("experience");
        Assertions.assertThat(updatedEvent).isNotSameAs(event);
    }

    @Test
    void shouldCreateNewEventWithUpdatedNbStars() {
        // Given
        EventVO concertEvent = new EventVO(1L, "Concert", "https://img.com", Set.of(), 5, "Great concert");

        // When
        EventVO updatedConcertNbStarsEvent = concertEvent.withNbStars(10);

        // Then
        Assertions.assertThat(updatedConcertNbStarsEvent.nbStars()).isEqualTo(10);
        Assertions.assertThat(updatedConcertNbStarsEvent).isNotSameAs(concertEvent);
    }

    @Test
    void shouldReturnTrue_whenMemberMatchingNameExists() {
        // Given
        BandVO rollingBeatsBand = new BandVO(1L, "The Rolling Beats", Set.of(new MemberVO(1L, "Queen Talia Bush")));
        BandVO shinyStarsBand = new BandVO(2L, "The Shiny Stars", Set.of(new MemberVO(2L, "Jarvis McCartney")));
        EventVO concertEvent = new EventVO(1L, "Concert", "https://img.com", Set.of(rollingBeatsBand, shinyStarsBand), 5, "Great concert");

        // When
        boolean hasMemberMatchingName = concertEvent.containsMemberMatchingName("McCartney");

        // Then
        Assertions.assertThat(hasMemberMatchingName).isTrue();
    }

    @Test
    void shouldReturnFalse_whenNoMemberMatchesName() {
        // Given
        BandVO rollingBeatsBand = new BandVO(1L, "The Rolling Beats", Set.of(new MemberVO(1L, "Queen Talia Bush")));
        EventVO concertEvent = new EventVO(1L, "Concert", "https://img.com", Set.of(rollingBeatsBand), 5, "Great concert");

        // When
        boolean result = concertEvent.containsMemberMatchingName("Dunlap");

        // Then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    void shouldCreateNewEventWithUpdatedBands() {
        // Given
        BandVO rollingBeatsBand = new BandVO(1L, "The Rolling Beats", Set.of(new MemberVO(1L, "Queen Talia Bush")));
        BandVO shinyStarsBand = new BandVO(2L, "The Shiny Stars", Set.of(new MemberVO(2L, "Jarvis McCartney")));
        EventVO concertEvent = new EventVO(1L, "Concert", "https://img.com", Set.of(rollingBeatsBand), 5, "Great concert");

        // When
        EventVO updatedEvent = concertEvent.withBands(Set.of(rollingBeatsBand, shinyStarsBand));

        // Then
        Assertions.assertThat(updatedEvent.bands()).containsExactlyInAnyOrder(rollingBeatsBand, shinyStarsBand);
        Assertions.assertThat(updatedEvent).isNotSameAs(concertEvent);
    }

    @Test
    void shouldReturnFalse_whenNbStarsLessThanZero() {
        // Given
        EventVO event = new EventVO(1L, "Concert", "https://img.com", Set.of(), -1, "Great concert");

        // When
        Boolean isNbStarsValid = event.isNbStarsValid();

        // Then
        Assertions.assertThat(isNbStarsValid).isNotNull().isFalse();
    }

    @Test
    void shouldReturnFalse_whenNbStarsGreaterThanFive() {
        // Given
        EventVO event = new EventVO(1L, "Concert", "https://img.com", Set.of(), 6, "Great concert");

        // When
        Boolean isNbStarsValid = event.isNbStarsValid();

        // Then
        Assertions.assertThat(isNbStarsValid).isNotNull().isFalse();
    }

    @Test
    void shouldReturnTrue_whenNbStarsBeetweenZeroAndFive() {
        // Given
        EventVO event = new EventVO(1L, "Concert", "https://img.com", Set.of(), 5, "Great concert");

        // When
        Boolean isNbStarsValid = event.isNbStarsValid();

        // Then
        Assertions.assertThat(isNbStarsValid).isNotNull().isTrue();
    }
}