package softuni.aggregator.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import softuni.aggregator.constants.ErrorMessages;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ErrorMessages.GENERAL_ERROR)
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}