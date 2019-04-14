package softuni.aggregator.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import softuni.aggregator.constants.ErrorMessages;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = ErrorMessages.UNAUTHORIZED)
public class ForbiddenActionException extends RuntimeException {

    public ForbiddenActionException(String message) {
        super(message);
    }
}