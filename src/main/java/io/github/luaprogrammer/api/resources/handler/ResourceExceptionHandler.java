package io.github.luaprogrammer.api.resources.handler;

import io.github.luaprogrammer.api.services.exceptions.DataIntegrityViolationException;
import io.github.luaprogrammer.api.services.exceptions.ObjectNotFoundException;
import io.github.luaprogrammer.api.services.exceptions.RuleBusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";
    private static final String CONSTANT_VALIDATION_NOT_NULL = "NotNull";
    private static final String CONSTANT_VALIDATION_LENGTH = "Length";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Errors> errors = errors(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StanderError> handlerObjectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
        StanderError error =
                new StanderError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RuleBusinessException.class)
    public ResponseEntity<StanderError> handlerDataIntegrityViolationException(RuleBusinessException ex,
                                                                               HttpServletRequest request) {
        StanderError error =
                new StanderError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StanderError> handlerDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {

        StanderError error =
                new StanderError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private List<Errors> errors(BindingResult bindingResult) {
        List<Errors> errors = new ArrayList<>();

        bindingResult.getFieldErrors().forEach(fieldErrors -> {
            String msg = handleUserErrorMessage(fieldErrors);
            errors.add(new Errors(msg));
        });

        return errors;
    }

    private String handleUserErrorMessage(FieldError fieldErrors) {
        if (Objects.equals(fieldErrors.getCode(), CONSTANT_VALIDATION_NOT_BLANK)) {
            return Objects.requireNonNull(fieldErrors.getDefaultMessage()).concat("é obrigatório.");
        }
        if (Objects.equals(fieldErrors.getCode(), CONSTANT_VALIDATION_NOT_NULL)) {
            return Objects.requireNonNull(fieldErrors.getDefaultMessage()).concat("não pode ser nulo.");
        }
        if (Objects.equals(fieldErrors.getCode(), CONSTANT_VALIDATION_LENGTH)) {
            return Objects.requireNonNull(fieldErrors.getDefaultMessage()).concat(
                    String.format("deve estar entre %s e %s caracteres", Objects.requireNonNull(fieldErrors.getArguments())[2],
                            fieldErrors.getArguments()[1]));
        }
        return fieldErrors.toString();
    }
}