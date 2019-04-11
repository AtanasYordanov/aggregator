package softuni.aggregator.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You are not authorized to do that!")
public class ForbiddenActionException extends RuntimeException {

    public ForbiddenActionException(String message) {
        super(message);
    }
}