package com.souzs.ds_catalog.resources.exceptions;

import com.souzs.ds_catalog.services.exceptions.DatabaseException;
import com.souzs.ds_catalog.services.exceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StanderError> entityNotFound(EntityNotFoundException e, HttpServletRequest r) {
        int stt = HttpStatus.NOT_FOUND.value();
        StanderError err = new StanderError(
                Instant.now(),
                stt,
                "Recurso não encontrado.",
                e.getMessage(),
                r.getRequestURI()
        );

        return ResponseEntity.status(stt).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StanderError> database(DatabaseException e, HttpServletRequest r) {
        int stt = HttpStatus.BAD_REQUEST.value();
        StanderError err = new StanderError(
                Instant.now(),
                stt,
                "Falha de integridade referencial.",
                e.getMessage(),
                r.getRequestURI()
        );

        return ResponseEntity.status(stt).body(err);
    }
}
