package adeo.leroymerlin.cdp.domain.event.exception;


public class EventNotFoundException extends Exception {

    public EventNotFoundException(Long id) {
        super(String.format("Event %s not found", id));
    }
}
