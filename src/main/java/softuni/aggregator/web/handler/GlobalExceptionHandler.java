package softuni.aggregator.web.handler;

import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.web.exceptions.ForbiddenActionException;
import softuni.aggregator.web.exceptions.NotFoundException;
import softuni.aggregator.web.exceptions.ServiceException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(HttpServletRequest request, Exception ex) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.warn("User {} tried to access non-existing resource: {}", principal.getEmail(), request.getRequestURL());
        return "error/404";
    }

    @ExceptionHandler(ForbiddenActionException.class)
    public String handleForbiddenException(HttpServletRequest request, Exception ex) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.warn("User tried to access forbidden page: {}", principal.getEmail() , request.getRequestURL());
        return "error/403";
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceException(HttpServletRequest request, Exception ex) {
        log.warn("Error when trying to access {}", request.getRequestURL());
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(HttpServletRequest request, Exception ex) {
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(HttpServletRequest request, Exception ex) {
        log.warn("Error when trying to access {}", request.getRequestURL());
        ex.printStackTrace();
        return new ResponseEntity<>(new Error("Something went wrong."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Error {

        private String message;
    }
}