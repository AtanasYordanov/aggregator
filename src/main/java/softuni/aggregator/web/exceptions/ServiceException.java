package softuni.aggregator.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something went wrong on our end!")
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}