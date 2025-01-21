package adeo.leroymerlin.cdp.domain;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberVOTest {

    @Test
    void shouldReturnTrueWhenNameContainsQueryCaseInsensitive() {
        // Given
        MemberVO haleemaPoolemember = new MemberVO(1L, "Queen Haleema Poole");

        // When
        boolean result = haleemaPoolemember.containsName("Haleema");

        // Then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNameDoesNotContainQuery() {
        // Given
        MemberVO member = new MemberVO(1L, "Queen Anika Walsh");

        // When
        boolean result = member.containsName("Haleema");

        // Then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueWhenQueryMatchesNamePartially() {
        // Given
        MemberVO member = new MemberVO(1L, "Queen Haleema Poole");

        // When
        boolean result = member.containsName("leem");

        // Then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void shouldThrowNullPointerExceptionWhenQueryIsNull() {
        // Given
        MemberVO member = new MemberVO(1L, "John Lennon");

        // When
        boolean containedName = member.containsName(null);

        // Then
        Assertions.assertThat(containedName).isFalse();
    }
}