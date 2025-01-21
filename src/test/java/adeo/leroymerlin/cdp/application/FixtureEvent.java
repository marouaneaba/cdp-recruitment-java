package adeo.leroymerlin.cdp.application;

import adeo.leroymerlin.cdp.domain.BandVO;
import adeo.leroymerlin.cdp.domain.MemberVO;
import adeo.leroymerlin.cdp.domain.event.EventVO;
import adeo.leroymerlin.cdp.infrastructure.Band;
import adeo.leroymerlin.cdp.infrastructure.Member;
import adeo.leroymerlin.cdp.infrastructure.event.Event;

import java.util.List;
import java.util.Set;

public class FixtureEvent {

    private FixtureEvent() {}

    public static EventVO createEventVo(Long id, String comment, Integer nbstars) {
        return new EventVO(
                id,
                "Concert Rock Night",
                "https://example.com/img1.jpg",
                Set.of(
                        new BandVO(1L, "The Rolling Beats", Set.of(
                                new MemberVO(1L, "John Lennon"),
                                new MemberVO(2L, "Paul McCartney")
                        )),
                        new BandVO(2L, "The Shiny Stars", Set.of(
                                new MemberVO(3L, "Mick Jagger"),
                                new MemberVO(4L, "Keith Richards"),
                                new MemberVO(1010L, "Queen Anika Walsh")
                        ))
                ),
                nbstars,
                comment
        );
    }

    public static List<EventVO> createEventVo() {
        EventVO concertEvent = new EventVO(
                1L,
                "Concert Rock Night",
                "https://example.com/img1.jpg",
                Set.of(
                        new BandVO(1L, "The Rolling Beats", Set.of(
                                new MemberVO(1L, "Jagger Lennon"),
                                new MemberVO(2L, "Paul McCartney")
                        )),
                        new BandVO(2L, "The Shiny Stars", Set.of(
                                new MemberVO(3L, "Mick Jagger"),
                                new MemberVO(4L, "Keith Richards"),
                                new MemberVO(1010L, "Queen Anika Walsh")
                        ))
                ),
                2,
                "Amazing !"
        );

        EventVO jazzEvent = new EventVO(
                2L,
                "Jazz Night Extravaganza",
                "https://example.com/img2.jpg",
                Set.of(
                        new BandVO(3L, "The Jazz Masters", Set.of(
                                new MemberVO(5L, "Miles Davis"),
                                new MemberVO(6L, "Richards Jagger")
                        )),
                        new BandVO(4L, "Swinging Stars", Set.of(
                                new MemberVO(7L, "Mick Jagger"),
                                new MemberVO(8L, "Ella Fitzgerald")
                        ))
                ),
                5,
                "A night to remember with timeless jazz classics"
        );
        return List.of(concertEvent, jazzEvent);
    }

    public static Event createEventEntity(Long eventId) {
        return new Event(
                eventId,
                "Concert Rock Night",
                "https://example.com/img1.jpg",
                Set.of(
                        new Band(1L, "The Rolling Beats", Set.of(
                                new Member(1L, "John Lennon"),
                                new Member(2L, "Paul McCartney")
                        )),
                        new Band(2L, "The Shiny Stars", Set.of(
                                new Member(3L, "Mick Jagger"),
                                new Member(4L, "Keith Richards")
                        ))
                ),
                4,
                "An amazing rock night with stellar performances!"
        );
    }
}
