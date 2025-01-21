package adeo.leroymerlin.cdp.infrastructure.event;

import adeo.leroymerlin.cdp.domain.event.EventRepository;
import adeo.leroymerlin.cdp.domain.event.EventVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EventDatabaseRepository implements EventRepository {

    private final EventJpaRepository eventJpaRepository;

    public EventDatabaseRepository(EventJpaRepository eventJpaRepository) {
        this.eventJpaRepository = eventJpaRepository;
    }


    @Override
    public List<EventVO> findAll() {
        return this.eventJpaRepository.findAll().stream()
                .map(EventEntityMapper::toEventVO).toList();
    }

    @Override
    public Optional<EventVO> findEventById(Long id) {
        return this.eventJpaRepository.findById(id)
                .map(EventEntityMapper::toEventVO);
    }

    @Override
    public void deleteById(Long id) {
        this.eventJpaRepository.deleteById(id);
    }

    @Override
    public void save(EventVO event) {
        this.eventJpaRepository.save(EventEntityMapper.toEventEntity(event));
    }
}
