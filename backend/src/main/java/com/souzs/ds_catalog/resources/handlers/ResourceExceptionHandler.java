package com.souzs.ds_catalog.resources.handlers;

import com.souzs.ds_catalog.dtos.errors.StandardError;
import com.souzs.ds_catalog.services.exceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

// Indica para o spring que assim que estourar uma excecao
// no nivel do service, essa classe e quem vai tratar a
// execacao lancada.
@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest http) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                e.getMessage(),
                http.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }
}
