package adeo.leroymerlin.cdp.domain.event;


import java.util.List;
import java.util.Optional;

public interface EventRepository {
    List<EventVO> findAll();

    Optional<EventVO> findEventById(Long id);

    void deleteById(Long id);

    void save(EventVO event);
}
