package softuni.aggregator.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import softuni.aggregator.constants.ErrorMessages;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorMessages.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}