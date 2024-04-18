package com.project.project_oop.config.error;

import com.project.project_oop.config.error.exception.InvalidArgumentException;
import com.project.project_oop.config.error.exception.ResourceFetchException;
import com.project.project_oop.config.error.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<APIError> handleError404(Exception e) {
        return buildResponseEntity(new APIError(HttpStatus.NOT_FOUND, "Requested resource does not exist", HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<APIError> handleIO(Exception e) {
        LOGGER.error("Exception Caused By: ", e);
        return buildResponseEntity(new APIError(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred in IO streams.", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIError> handleAccessDenied(Exception e) {
        return buildResponseEntity(new APIError(HttpStatus.UNAUTHORIZED, "Access denied.", HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<APIError> handleInvalidArgumentException(Exception e) {
        return buildResponseEntity(new APIError(HttpStatus.BAD_REQUEST, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ResourceFetchException.class)
    public ResponseEntity<APIError> handleResourceFetchException(Exception e) {
        LOGGER.error("Exception Caused By: ", e);
        return buildResponseEntity(new APIError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> handleResourceNotFoundException(Exception e) {
        return buildResponseEntity(new APIError(HttpStatus.NOT_FOUND, e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> handleValidationExceptions(MethodArgumentNotValidException e) {
        StringBuilder stringBuilder = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach(error -> stringBuilder.append(String.format("%s : %s ", ((FieldError) error).getField(), error.getDefaultMessage())));
        return buildResponseEntity(new APIError(HttpStatus.BAD_REQUEST, stringBuilder.toString(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleError(Exception e) {
        LOGGER.error("Exception Caused By: ", e);
        return buildResponseEntity(new APIError(HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getName() + " " + e.getMessage(), 500));
    }

    private ResponseEntity<APIError> buildResponseEntity(APIError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
