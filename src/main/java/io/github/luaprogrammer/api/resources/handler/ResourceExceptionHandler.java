package io.github.luaprogrammer.api.resources.handler;

import io.github.luaprogrammer.api.services.exceptions.ObjectNotFoundException;
import io.github.luaprogrammer.api.services.exceptions.RuleBusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StanderError> handlerObjectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
        StanderError error =
                new StanderError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RuleBusinessException.class)
    public ResponseEntity<StanderError> handlerDataIntegrityViolationException(RuleBusinessException ex, HttpServletRequest request) {
        StanderError error =
                new StanderError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}