package softuni.aggregator.web.handler;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import softuni.aggregator.web.exceptions.NotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(HttpServletRequest request, Exception ex) {
        log.warn("User tried to access non-existing resource: {}", request.getRequestURL());
        ex.printStackTrace();
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(HttpServletRequest request, Exception ex) {
        log.warn("Error when trying to access {}", request.getRequestURL());
        ex.printStackTrace();
        return "error/generic-error";
    }
}