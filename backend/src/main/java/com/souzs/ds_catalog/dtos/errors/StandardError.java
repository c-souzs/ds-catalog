package com.souzs.ds_catalog.dtos.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StandardError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;
}
