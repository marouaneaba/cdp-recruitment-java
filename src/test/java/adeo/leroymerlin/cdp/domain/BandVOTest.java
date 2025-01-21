package adeo.leroymerlin.cdp.domain;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class BandVOTest {

    @Test
    void shouldReturnTrue_whenMemberMatchesName() {
        // Given
        MemberVO avaDunlapMember = new MemberVO(1L, "Queen Ava Dunlap");
        MemberVO taliaBushmember = new MemberVO(2L, "Talia Bush");
        BandVO beatlesBand = new BandVO(1L, "The Beatles", Set.of(avaDunlapMember, taliaBushmember));

        // When
        boolean result = beatlesBand.containsMemberMatchingName("Bush");

        // Then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNoMemberMatchesName() {
        // Given
        MemberVO avaDunlapMember = new MemberVO(1L, "Queen Ava Dunlap");
        BandVO beatlesBand = new BandVO(1L, "The Beatles", Set.of(avaDunlapMember));

        // When
        boolean result = beatlesBand.containsMemberMatchingName("Talia");

        // Then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    void shouldCreateNewBandWithUpdatedMembers() {
        // Given
        MemberVO avaDunlapMember = new MemberVO(1L, "Queen Ava Dunlap");
        BandVO beatlesBand = new BandVO(1L, "The Beatles", Set.of(avaDunlapMember));

        MemberVO mcCartneyMember = new MemberVO(2L, "toor McCartney");

        // When
        BandVO updatedBand = beatlesBand.withMembers(Set.of(avaDunlapMember, mcCartneyMember));

        // Then
        Assertions.assertThat(updatedBand.members()).containsExactlyInAnyOrder(avaDunlapMember, mcCartneyMember);
        Assertions.assertThat(updatedBand).isNotSameAs(beatlesBand);
    }
}