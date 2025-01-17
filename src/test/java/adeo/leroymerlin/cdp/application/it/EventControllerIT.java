package adeo.leroymerlin.cdp.application.it;


import adeo.leroymerlin.cdp.infrastructure.event.Event;
import adeo.leroymerlin.cdp.infrastructure.event.EventJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/tx.html#testcontext-tx-enabling-transactions
class EventControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventJpaRepository eventJpaRepository;

    @Test
    void shouldReturn204_whenDeleteEvent() throws Exception {
        // Given

        // When
        this.mockMvc.perform(
                        delete("/api/events/{id}", 1000L)
                )
                // Then
                .andExpect(status().isNoContent());
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
    }

    @Test
    void shouldReturn204StatusAndUpdateEventComment() throws Exception {
        // Given
        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", 1000L)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "id": 1000,
                                            "title": "GrasPop Metal Meeting",
                                            "imgUrl": "img/1000.jpeg",
                                            "bands": [
                                                {
                                                    "id": 1004,
                                                    "name": "The Ramones",
                                                    "members": [
                                                        {
                                                            "id": 1016,
                                                            "name": "Queen Haleema Poole"
                                                        },
                                                        {
                                                            "id": 1015,
                                                            "name": "Queen Ava Dunlap"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1001,
                                                    "name": "Guns n roses",
                                                    "members": [
                                                        {
                                                            "id": 1007,
                                                            "name": "Queen Jasmine Collier"
                                                        },
                                                        {
                                                            "id": 1009,
                                                            "name": "Queen Aaliyah York"
                                                        },
                                                        {
                                                            "id": 1008,
                                                            "name": "Queen Daisy Burke"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1003,
                                                    "name": "Rolling Stones",
                                                    "members": [
                                                        {
                                                            "id": 1014,
                                                            "name": "Queen Talia Bush"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1002,
                                                    "name": "Metallica",
                                                    "members": [
                                                        {
                                                            "id": 1010,
                                                            "name": "Queen Anika Walsh"
                                                        },
                                                        {
                                                            "id": 1013,
                                                            "name": "Queen Constance Carroll"
                                                        },
                                                        {
                                                            "id": 1012,
                                                            "name": "Queen Aliyah Jarvis"
                                                        },
                                                        {
                                                            "id": 1011,
                                                            "name": "Queen Katy Stone"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1000,
                                                    "name": "Pink Floyd",
                                                    "members": [
                                                        {
                                                            "id": 1004,
                                                            "name": "Queen Stacey ODoherty (Asya)"
                                                        },
                                                        {
                                                            "id": 1002,
                                                            "name": "Queen Genevieve Clark"
                                                        },
                                                        {
                                                            "id": 1001,
                                                            "name": "Queen Frankie Gross (Fania)"
                                                        },
                                                        {
                                                            "id": 1005,
                                                            "name": "Queen Gertrude Hudson"
                                                        },
                                                        {
                                                            "id": 1003,
                                                            "name": "Queen Veronica Graves"
                                                        },
                                                        {
                                                            "id": 1006,
                                                            "name": "Queen Madeleine Taylor"
                                                        }
                                                    ]
                                                }
                                            ],
                                            "nbStars": null,
                                            "comment": "new comment"
                                        }
                                        """)
                )
                // Then
                .andExpect(status().isNoContent());

        Optional<Event> eventById = this.eventJpaRepository.findById(1000L);
        Assertions.assertThat(eventById).isPresent();
        Assertions.assertThat(eventById.get().getId()).isEqualTo(1000L);
        // The nbStars should retain its value because it is initialized to null when inserted into the database
        Assertions.assertThat(eventById.get().getNbStars()).isNull();
        Assertions.assertThat(eventById.get().getComment()).isEqualTo("new comment");
    }

    @Test
    void shouldReturn204StatusAndUpdateNbStars() throws Exception {
        // Given

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", 1000)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "id": 1000,
                                            "title": "GrasPop Metal Meeting",
                                            "imgUrl": "img/1000.jpeg",
                                            "bands": [
                                                {
                                                    "id": 1004,
                                                    "name": "The Ramones",
                                                    "members": [
                                                        {
                                                            "id": 1016,
                                                            "name": "Queen Haleema Poole"
                                                        },
                                                        {
                                                            "id": 1015,
                                                            "name": "Queen Ava Dunlap"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1001,
                                                    "name": "Guns n roses",
                                                    "members": [
                                                        {
                                                            "id": 1007,
                                                            "name": "Queen Jasmine Collier"
                                                        },
                                                        {
                                                            "id": 1009,
                                                            "name": "Queen Aaliyah York"
                                                        },
                                                        {
                                                            "id": 1008,
                                                            "name": "Queen Daisy Burke"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1003,
                                                    "name": "Rolling Stones",
                                                    "members": [
                                                        {
                                                            "id": 1014,
                                                            "name": "Queen Talia Bush"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1002,
                                                    "name": "Metallica",
                                                    "members": [
                                                        {
                                                            "id": 1010,
                                                            "name": "Queen Anika Walsh"
                                                        },
                                                        {
                                                            "id": 1013,
                                                            "name": "Queen Constance Carroll"
                                                        },
                                                        {
                                                            "id": 1012,
                                                            "name": "Queen Aliyah Jarvis"
                                                        },
                                                        {
                                                            "id": 1011,
                                                            "name": "Queen Katy Stone"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1000,
                                                    "name": "Pink Floyd",
                                                    "members": [
                                                        {
                                                            "id": 1004,
                                                            "name": "Queen Stacey ODoherty (Asya)"
                                                        },
                                                        {
                                                            "id": 1002,
                                                            "name": "Queen Genevieve Clark"
                                                        },
                                                        {
                                                            "id": 1001,
                                                            "name": "Queen Frankie Gross (Fania)"
                                                        },
                                                        {
                                                            "id": 1005,
                                                            "name": "Queen Gertrude Hudson"
                                                        },
                                                        {
                                                            "id": 1003,
                                                            "name": "Queen Veronica Graves"
                                                        },
                                                        {
                                                            "id": 1006,
                                                            "name": "Queen Madeleine Taylor"
                                                        }
                                                    ]
                                                }
                                            ],
                                            "nbStars": 5,
                                            "comment": null
                                        }
                                        """)
                )
                // Then
                .andExpect(status().isNoContent());

        Optional<Event> eventById = this.eventJpaRepository.findById(1000L);
        Assertions.assertThat(eventById).isPresent();
        Assertions.assertThat(eventById.get().getId()).isEqualTo(1000L);
        Assertions.assertThat(eventById.get().getNbStars()).isEqualTo(5);
        // The comment should retain its value because it is initialized to null when inserted into the database
        Assertions.assertThat(eventById.get().getComment()).isNull();
    }

    @Test
    void shouldReturn400_whenUpdateEventIdPathIsBlank() throws Exception {
        // Given

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", " ")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "id": 1000,
                                            "title": "GrasPop Metal Meeting",
                                            "imgUrl": "img/1000.jpeg",
                                            "bands": [
                                                {
                                                    "id": 1004,
                                                    "name": "The Ramones",
                                                    "members": [
                                                        {
                                                            "id": 1016,
                                                            "name": "Queen Haleema Poole"
                                                        },
                                                        {
                                                            "id": 1015,
                                                            "name": "Queen Ava Dunlap"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1001,
                                                    "name": "Guns n roses",
                                                    "members": [
                                                        {
                                                            "id": 1007,
                                                            "name": "Queen Jasmine Collier"
                                                        },
                                                        {
                                                            "id": 1009,
                                                            "name": "Queen Aaliyah York"
                                                        },
                                                        {
                                                            "id": 1008,
                                                            "name": "Queen Daisy Burke"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1003,
                                                    "name": "Rolling Stones",
                                                    "members": [
                                                        {
                                                            "id": 1014,
                                                            "name": "Queen Talia Bush"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1002,
                                                    "name": "Metallica",
                                                    "members": [
                                                        {
                                                            "id": 1010,
                                                            "name": "Queen Anika Walsh"
                                                        },
                                                        {
                                                            "id": 1013,
                                                            "name": "Queen Constance Carroll"
                                                        },
                                                        {
                                                            "id": 1012,
                                                            "name": "Queen Aliyah Jarvis"
                                                        },
                                                        {
                                                            "id": 1011,
                                                            "name": "Queen Katy Stone"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1000,
                                                    "name": "Pink Floyd",
                                                    "members": [
                                                        {
                                                            "id": 1004,
                                                            "name": "Queen Stacey ODoherty (Asya)"
                                                        },
                                                        {
                                                            "id": 1002,
                                                            "name": "Queen Genevieve Clark"
                                                        },
                                                        {
                                                            "id": 1001,
                                                            "name": "Queen Frankie Gross (Fania)"
                                                        },
                                                        {
                                                            "id": 1005,
                                                            "name": "Queen Gertrude Hudson"
                                                        },
                                                        {
                                                            "id": 1003,
                                                            "name": "Queen Veronica Graves"
                                                        },
                                                        {
                                                            "id": 1006,
                                                            "name": "Queen Madeleine Taylor"
                                                        }
                                                    ]
                                                }
                                            ],
                                            "nbStars": 2,
                                            "comment": null
                                        }
                                        """)
                )
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn405_whenUpdateEventAndPathIdIsEmpty() throws Exception {
        // Given

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", "")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "id": 1000,
                                            "title": "GrasPop Metal Meeting",
                                            "imgUrl": "img/1000.jpeg",
                                            "bands": [
                                                {
                                                    "id": 1004,
                                                    "name": "The Ramones",
                                                    "members": [
                                                        {
                                                            "id": 1016,
                                                            "name": "Queen Haleema Poole"
                                                        },
                                                        {
                                                            "id": 1015,
                                                            "name": "Queen Ava Dunlap"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1001,
                                                    "name": "Guns n roses",
                                                    "members": [
                                                        {
                                                            "id": 1007,
                                                            "name": "Queen Jasmine Collier"
                                                        },
                                                        {
                                                            "id": 1009,
                                                            "name": "Queen Aaliyah York"
                                                        },
                                                        {
                                                            "id": 1008,
                                                            "name": "Queen Daisy Burke"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1003,
                                                    "name": "Rolling Stones",
                                                    "members": [
                                                        {
                                                            "id": 1014,
                                                            "name": "Queen Talia Bush"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1002,
                                                    "name": "Metallica",
                                                    "members": [
                                                        {
                                                            "id": 1010,
                                                            "name": "Queen Anika Walsh"
                                                        },
                                                        {
                                                            "id": 1013,
                                                            "name": "Queen Constance Carroll"
                                                        },
                                                        {
                                                            "id": 1012,
                                                            "name": "Queen Aliyah Jarvis"
                                                        },
                                                        {
                                                            "id": 1011,
                                                            "name": "Queen Katy Stone"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1000,
                                                    "name": "Pink Floyd",
                                                    "members": [
                                                        {
                                                            "id": 1004,
                                                            "name": "Queen Stacey ODoherty (Asya)"
                                                        },
                                                        {
                                                            "id": 1002,
                                                            "name": "Queen Genevieve Clark"
                                                        },
                                                        {
                                                            "id": 1001,
                                                            "name": "Queen Frankie Gross (Fania)"
                                                        },
                                                        {
                                                            "id": 1005,
                                                            "name": "Queen Gertrude Hudson"
                                                        },
                                                        {
                                                            "id": 1003,
                                                            "name": "Queen Veronica Graves"
                                                        },
                                                        {
                                                            "id": 1006,
                                                            "name": "Queen Madeleine Taylor"
                                                        }
                                                    ]
                                                }
                                            ],
                                            "nbStars": 2,
                                            "comment": null
                                        }
                                        """)
                )
                // Then
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void shouldReturn400Status_whenNbStarsIsLessThanZero() throws Exception {
        // Given

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", 1000)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "id": 1000,
                                            "title": "GrasPop Metal Meeting",
                                            "imgUrl": "img/1000.jpeg",
                                            "bands": [
                                                {
                                                    "id": 1004,
                                                    "name": "The Ramones",
                                                    "members": [
                                                        {
                                                            "id": 1016,
                                                            "name": "Queen Haleema Poole"
                                                        },
                                                        {
                                                            "id": 1015,
                                                            "name": "Queen Ava Dunlap"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1001,
                                                    "name": "Guns n roses",
                                                    "members": [
                                                        {
                                                            "id": 1007,
                                                            "name": "Queen Jasmine Collier"
                                                        },
                                                        {
                                                            "id": 1009,
                                                            "name": "Queen Aaliyah York"
                                                        },
                                                        {
                                                            "id": 1008,
                                                            "name": "Queen Daisy Burke"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1003,
                                                    "name": "Rolling Stones",
                                                    "members": [
                                                        {
                                                            "id": 1014,
                                                            "name": "Queen Talia Bush"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1002,
                                                    "name": "Metallica",
                                                    "members": [
                                                        {
                                                            "id": 1010,
                                                            "name": "Queen Anika Walsh"
                                                        },
                                                        {
                                                            "id": 1013,
                                                            "name": "Queen Constance Carroll"
                                                        },
                                                        {
                                                            "id": 1012,
                                                            "name": "Queen Aliyah Jarvis"
                                                        },
                                                        {
                                                            "id": 1011,
                                                            "name": "Queen Katy Stone"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1000,
                                                    "name": "Pink Floyd",
                                                    "members": [
                                                        {
                                                            "id": 1004,
                                                            "name": "Queen Stacey ODoherty (Asya)"
                                                        },
                                                        {
                                                            "id": 1002,
                                                            "name": "Queen Genevieve Clark"
                                                        },
                                                        {
                                                            "id": 1001,
                                                            "name": "Queen Frankie Gross (Fania)"
                                                        },
                                                        {
                                                            "id": 1005,
                                                            "name": "Queen Gertrude Hudson"
                                                        },
                                                        {
                                                            "id": 1003,
                                                            "name": "Queen Veronica Graves"
                                                        },
                                                        {
                                                            "id": 1006,
                                                            "name": "Queen Madeleine Taylor"
                                                        }
                                                    ]
                                                }
                                            ],
                                            "nbStars": -2,
                                            "comment": "new comment"
                                        }
                                        """)
                )
                // Then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.error_description").value("Number of stars must be between 0 and 5"));

        Optional<Event> eventById = this.eventJpaRepository.findById(1000L);
        Assertions.assertThat(eventById).isPresent();
        Assertions.assertThat(eventById.get().getId()).isEqualTo(1000L);
        // The comment and nbStars should retain its value because it is initialized to null when inserted into the database
        Assertions.assertThat(eventById.get().getNbStars()).isNull();
        Assertions.assertThat(eventById.get().getComment()).isNull();
    }

    @Test
    void shouldReturn400Status_whenNbStarsIsGreaterThanFive() throws Exception {
        // Given

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", 1000)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "id": 1000,
                                            "title": "GrasPop Metal Meeting",
                                            "imgUrl": "img/1000.jpeg",
                                            "bands": [
                                                {
                                                    "id": 1004,
                                                    "name": "The Ramones",
                                                    "members": [
                                                        {
                                                            "id": 1016,
                                                            "name": "Queen Haleema Poole"
                                                        },
                                                        {
                                                            "id": 1015,
                                                            "name": "Queen Ava Dunlap"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1001,
                                                    "name": "Guns n roses",
                                                    "members": [
                                                        {
                                                            "id": 1007,
                                                            "name": "Queen Jasmine Collier"
                                                        },
                                                        {
                                                            "id": 1009,
                                                            "name": "Queen Aaliyah York"
                                                        },
                                                        {
                                                            "id": 1008,
                                                            "name": "Queen Daisy Burke"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1003,
                                                    "name": "Rolling Stones",
                                                    "members": [
                                                        {
                                                            "id": 1014,
                                                            "name": "Queen Talia Bush"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1002,
                                                    "name": "Metallica",
                                                    "members": [
                                                        {
                                                            "id": 1010,
                                                            "name": "Queen Anika Walsh"
                                                        },
                                                        {
                                                            "id": 1013,
                                                            "name": "Queen Constance Carroll"
                                                        },
                                                        {
                                                            "id": 1012,
                                                            "name": "Queen Aliyah Jarvis"
                                                        },
                                                        {
                                                            "id": 1011,
                                                            "name": "Queen Katy Stone"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1000,
                                                    "name": "Pink Floyd",
                                                    "members": [
                                                        {
                                                            "id": 1004,
                                                            "name": "Queen Stacey ODoherty (Asya)"
                                                        },
                                                        {
                                                            "id": 1002,
                                                            "name": "Queen Genevieve Clark"
                                                        },
                                                        {
                                                            "id": 1001,
                                                            "name": "Queen Frankie Gross (Fania)"
                                                        },
                                                        {
                                                            "id": 1005,
                                                            "name": "Queen Gertrude Hudson"
                                                        },
                                                        {
                                                            "id": 1003,
                                                            "name": "Queen Veronica Graves"
                                                        },
                                                        {
                                                            "id": 1006,
                                                            "name": "Queen Madeleine Taylor"
                                                        }
                                                    ]
                                                }
                                            ],
                                            "nbStars": 6,
                                            "comment": "new comment"
                                        }
                                        """)
                )
                // Then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.error_description").value("Number of stars must be between 0 and 5"));

        Optional<Event> eventById = this.eventJpaRepository.findById(1000L);
        Assertions.assertThat(eventById).isPresent();
        Assertions.assertThat(eventById.get().getId()).isEqualTo(1000L);
        // The comment should retain its value because it is initialized to null when inserted into the database
        Assertions.assertThat(eventById.get().getNbStars()).isNull();
        Assertions.assertThat(eventById.get().getComment()).isNull();
    }

    @Test
    void shouldReturn404_whenUpdateEventAndNotFoundEventId() throws Exception {
        // Given

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", 999)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "id": 1000,
                                            "title": "GrasPop Metal Meeting",
                                            "imgUrl": "img/1000.jpeg",
                                            "bands": [
                                                {
                                                    "id": 1004,
                                                    "name": "The Ramones",
                                                    "members": [
                                                        {
                                                            "id": 1016,
                                                            "name": "Queen Haleema Poole"
                                                        },
                                                        {
                                                            "id": 1015,
                                                            "name": "Queen Ava Dunlap"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1001,
                                                    "name": "Guns n roses",
                                                    "members": [
                                                        {
                                                            "id": 1007,
                                                            "name": "Queen Jasmine Collier"
                                                        },
                                                        {
                                                            "id": 1009,
                                                            "name": "Queen Aaliyah York"
                                                        },
                                                        {
                                                            "id": 1008,
                                                            "name": "Queen Daisy Burke"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1003,
                                                    "name": "Rolling Stones",
                                                    "members": [
                                                        {
                                                            "id": 1014,
                                                            "name": "Queen Talia Bush"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1002,
                                                    "name": "Metallica",
                                                    "members": [
                                                        {
                                                            "id": 1010,
                                                            "name": "Queen Anika Walsh"
                                                        },
                                                        {
                                                            "id": 1013,
                                                            "name": "Queen Constance Carroll"
                                                        },
                                                        {
                                                            "id": 1012,
                                                            "name": "Queen Aliyah Jarvis"
                                                        },
                                                        {
                                                            "id": 1011,
                                                            "name": "Queen Katy Stone"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": 1000,
                                                    "name": "Pink Floyd",
                                                    "members": [
                                                        {
                                                            "id": 1004,
                                                            "name": "Queen Stacey ODoherty (Asya)"
                                                        },
                                                        {
                                                            "id": 1002,
                                                            "name": "Queen Genevieve Clark"
                                                        },
                                                        {
                                                            "id": 1001,
                                                            "name": "Queen Frankie Gross (Fania)"
                                                        },
                                                        {
                                                            "id": 1005,
                                                            "name": "Queen Gertrude Hudson"
                                                        },
                                                        {
                                                            "id": 1003,
                                                            "name": "Queen Veronica Graves"
                                                        },
                                                        {
                                                            "id": 1006,
                                                            "name": "Queen Madeleine Taylor"
                                                        }
                                                    ]
                                                }
                                            ],
                                            "nbStars": 2,
                                            "comment": null
                                        }
                                        """)
                )
                // Then
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.error_description").value("Event 999 not found"));

    }

    @Test
    void shouldReturn400_whenUpdateEventAndEventBodyIsNull() throws Exception {
        // Given

        // When
        this.mockMvc.perform(
                        put("/api/events/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("")
                )
                // Then
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturn200AndEvents_whenMembersContainingKeyword() throws Exception {
        // Given

        // When
        mockMvc.perform(
                        get("/api/events/search/{keyword}", "wa")
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].title").value("GrasPop Metal Meeting [1]"))
                .andExpect(jsonPath("$[0].bands[0].name").value("Metallica [1]"))
                .andExpect(jsonPath("$[0].bands[0].members[0].name").value("Queen Anika Walsh"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].bands.length()").value(1))
                .andExpect(jsonPath("$[0].bands[0].members.length()").value(1));
    }

    @Test
    void shouldReturn200AndZeroEvent_whenMembersNotContainingKeyword() throws Exception {
        // Given

        // When
        mockMvc.perform(
                        get("/api/events/search/{keyword}", "abc")
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturn400_whenQueryIsBlank() throws Exception {
        // Given

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