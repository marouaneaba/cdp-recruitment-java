package adeo.leroymerlin.cdp.infrastructure.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EventJpaRepository extends JpaRepository<Event, Long> {
    void deleteById(Long eventId);
}
