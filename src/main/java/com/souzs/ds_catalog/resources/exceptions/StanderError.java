package com.souzs.ds_catalog.resources.exceptions;

import java.time.Instant;

public record StanderError(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
