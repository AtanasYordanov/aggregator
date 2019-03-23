package softuni.aggregator.web.handler;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleSQLException(HttpServletRequest request, Exception ex) {
        log.warn("Error when trying to access {}: {}", request.getRequestURL(), ex.getMessage());
        return "error";
    }
}