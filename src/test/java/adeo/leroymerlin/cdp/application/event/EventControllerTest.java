package adeo.leroymerlin.cdp.application.event;


import adeo.leroymerlin.cdp.application.FixtureEvent;
import adeo.leroymerlin.cdp.domain.event.EventManagementPort;
import adeo.leroymerlin.cdp.domain.event.EventVO;
import adeo.leroymerlin.cdp.domain.event.exception.EventDetailsInvalidException;
import adeo.leroymerlin.cdp.domain.event.exception.EventNotFoundException;
import adeo.leroymerlin.cdp.domain.event.exception.QueryForEventSearchException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EventController.class)
class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventManagementPort eventManagementPort;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn204_whenDeleteEvent() throws Exception {
        // Given
        Mockito.doNothing().when(eventManagementPort).delete(isA(Long.class));

        // When
        this.mockMvc.perform(
                delete("/api/events/{id}", 1000)
        )
                // Then
                .andExpect(status().isNoContent());
        Mockito.verify(eventManagementPort, Mockito.times(1)).delete(isA(Long.class));
    }

    @Test
    void shouldReturn400_whenDeleteEventIdBlank() throws Exception {
        // Given

        // When
        this.mockMvc.perform(
                        delete("/api/events/{id}", "  ")
                )
                // Then
                .andExpect(status().isBadRequest());
        Mockito.verify(eventManagementPort, Mockito.times(0)).delete(isA(Long.class));
    }

    @Test
    void shouldReturn200SAndFetchEvent() throws Exception {
        // Given
        Mockito.when(eventManagementPort.getEvents()).thenReturn(FixtureEvent.createEventVo());

        // When
        this.mockMvc.perform(
                get("/api/events/")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(FixtureEvent.createEventVo())));

        Mockito.verify(eventManagementPort, Mockito.times(1)).getEvents();
    }

    @Test
    void shouldReturn204StatusAndUpdateComment() throws Exception {
        // Given
        Mockito.doNothing().when(eventManagementPort).updateEventWithCommentAndStars(isA(Long.class), isA(EventVO.class));
        ArgumentCaptor<EventVO> updateEventCaptor = ArgumentCaptor.forClass(EventVO.class);

        // When
        this.mockMvc.perform(
                put("/api/events/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(FixtureEvent.createEventVo(1L, "new comment", null)))
        )
                // Then
                .andExpect(status().isNoContent());

        Mockito.verify(eventManagementPort, Mockito.times(1)).updateEventWithCommentAndStars(isA(Long.class), updateEventCaptor.capture());
        EventVO capturedEvent = updateEventCaptor.getValue();

        Assertions.assertThat(capturedEvent.comment()).isEqualTo("new comment");
        Assertions.assertThat(capturedEvent.nbStars()).isNull();
    }

    @Test
    void shouldReturn204StatusAndUpdateNbStars() throws Exception {
        // Given
        Mockito.doNothing().when(eventManagementPort).updateEventWithCommentAndStars(isA(Long.class), isA(EventVO.class));
        ArgumentCaptor<EventVO> updateEventCaptor = ArgumentCaptor.forClass(EventVO.class);

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(FixtureEvent.createEventVo(1L, "", 5)))
                )
                // Then
                .andExpect(status().isNoContent());

        Mockito.verify(eventManagementPort, Mockito.times(1)).updateEventWithCommentAndStars(isA(Long.class), updateEventCaptor.capture());
        EventVO capturedEvent = updateEventCaptor.getValue();

        Assertions.assertThat(capturedEvent.comment()).isEmpty();
        Assertions.assertThat(capturedEvent.nbStars()).isEqualTo(5);
    }

    @Test
    void shouldReturn400Status_whenNbStarsIsLessThanZero() throws Exception {
        // Given
        Mockito.doThrow(new EventDetailsInvalidException("Number of stars must be between 0 and 5")).when(eventManagementPort).updateEventWithCommentAndStars(isA(Long.class), isA(EventVO.class));

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(FixtureEvent.createEventVo(1L, "", -1)))
                )
                // Then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.error_description").value("Number of stars must be between 0 and 5"));

        Mockito.verify(eventManagementPort, Mockito.times(1)).updateEventWithCommentAndStars(isA(Long.class), isA(EventVO.class));
    }

    @Test
    void shouldReturn400Status_whenNbStarsIsGreaterThanFive() throws Exception {
        // Given
        Mockito.doThrow(new EventDetailsInvalidException("Number of stars must be between 0 and 5")).when(eventManagementPort).updateEventWithCommentAndStars(isA(Long.class), isA(EventVO.class));

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(FixtureEvent.createEventVo(1L, "", 6)))
                )
                // Then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.error_description").value("Number of stars must be between 0 and 5"));

        Mockito.verify(eventManagementPort, Mockito.times(1)).updateEventWithCommentAndStars(isA(Long.class), isA(EventVO.class));
    }

    @Test
    void shouldReturn400_whenEventIdPathIsBlank() throws Exception {
        // Given

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", " ")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(FixtureEvent.createEventVo(1L, "new comment", 5)))
                )
                // Then
                .andExpect(status().isBadRequest());

        Mockito.verify(eventManagementPort, Mockito.times(0)).updateEventWithCommentAndStars(isA(Long.class), isA(EventVO.class));
    }

    @Test
    void shouldReturn405_whenPathEventIdIsEmpty() throws Exception {
        // Given

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", "")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(FixtureEvent.createEventVo(1L, "new comment", 5)))
                )
                // Then
                .andExpect(status().isMethodNotAllowed());

        Mockito.verify(eventManagementPort, Mockito.times(0)).updateEventWithCommentAndStars(isA(Long.class), isA(EventVO.class));
    }

    @Test
    void shouldReturn404_whenNotFoundEventId() throws Exception {
        // Given

        ArgumentCaptor<EventVO> updateEventCaptor = ArgumentCaptor.forClass(EventVO.class);
        Mockito.doThrow(new EventNotFoundException(999L)).when(eventManagementPort).updateEventWithCommentAndStars(isA(Long.class), isA(EventVO.class));


        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", 999)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(FixtureEvent.createEventVo(999L, "comment", 5)))
                )
                // Then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.error_description").value("Event 999 not found"));

        Mockito.verify(eventManagementPort, Mockito.times(1)).updateEventWithCommentAndStars(isA(Long.class), updateEventCaptor.capture());
        EventVO capturedEvent = updateEventCaptor.getValue();

        Assertions.assertThat(capturedEvent.comment()).isEqualTo("comment");
        Assertions.assertThat(capturedEvent.nbStars()).isEqualTo(5);
    }

    @Test
    void shouldReturn400_whenEventIdTypeError() throws Exception {
        // Given

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", "abc")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(FixtureEvent.createEventVo(999L, "comment", 5)))
                )
                // Then
                .andExpect(status().isBadRequest());

        Mockito.verify(eventManagementPort, Mockito.times(0)).updateEventWithCommentAndStars(any(), any());
    }

    @Test
    void shouldReturn400_whenEventBodyIsNull() throws Exception {
        // Given
        Mockito.doThrow(new EventDetailsInvalidException("Event ID and Event details cannot be null")).when(eventManagementPort).updateEventWithCommentAndStars(isA(Long.class), isA(EventVO.class));
        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", 1000)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("")
                )
                // Then
                .andExpect(status().isBadRequest());

        Mockito.verify(eventManagementPort, Mockito.times(0)).updateEventWithCommentAndStars(isA(Long.class), isA(EventVO.class));
    }

    @Test
    void shouldReturn200_whenMembersContainingKeyword() throws Exception {
        // Given
        Mockito.when(eventManagementPort.searchEventsByMemberName("wa")).thenReturn(FixtureEvent.createEventVo());
        // When
        mockMvc.perform(
                        get("/api/events/search/{keyword}", "wa")
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturn200_whenMembersNotContainingKeyword() throws Exception {
        // Given
        Mockito.when(eventManagementPort.searchEventsByMemberName("wa")).thenReturn(Collections.emptyList());

        // When
        mockMvc.perform(
                        get("/api/events/search/{keyword}", "wa")
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturn400_whenQueryIsBlank() throws Exception {
        // Given
        Mockito.when(eventManagementPort.searchEventsByMemberName(" ")).thenThrow(new QueryForEventSearchException("Cannot search for events with an empty or null query."));

        // When
        mockMvc.perform(
                        get("/api/events/search/{keyword}", " ")
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.error_description").value("Cannot search for events with an empty or null query."));
    }
}