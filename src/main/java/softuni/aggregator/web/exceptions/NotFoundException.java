package softuni.aggregator.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "We couldn't find what you were looking for!")
public class NotFoundException extends RuntimeException {
}