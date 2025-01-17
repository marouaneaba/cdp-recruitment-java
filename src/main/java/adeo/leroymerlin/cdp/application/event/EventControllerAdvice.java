package adeo.leroymerlin.cdp.application.event;

import adeo.leroymerlin.cdp.application.ApplicationError;
import adeo.leroymerlin.cdp.domain.event.exception.EventDetailsInvalidException;
import adeo.leroymerlin.cdp.domain.event.exception.EventNotFoundException;
import adeo.leroymerlin.cdp.domain.event.exception.QueryForEventSearchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice(assignableTypes = {EventController.class})
public class EventControllerAdvice {


    private static final Logger log = LoggerFactory.getLogger(EventControllerAdvice.class);

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApplicationError handleEventNotFoundException(Exception e) {
        log.info(e.getMessage());
        return new ApplicationError(NOT_FOUND.name(), e.getMessage());
    }

    @ExceptionHandler(EventDetailsInvalidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApplicationError handleEventDetailsInvalidException(Exception e) {
        log.info(e.getMessage());
        return new ApplicationError(BAD_REQUEST.name(), e.getMessage());
    }

    @ExceptionHandler(QueryForEventSearchException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApplicationError handleQueryForEventSearchException(Exception e) {
        log.info(e.getMessage());
        return new ApplicationError(BAD_REQUEST.name(), e.getMessage());
    }
}
