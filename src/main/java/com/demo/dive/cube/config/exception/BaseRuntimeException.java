package com.demo.dive.cube.config.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRuntimeException extends RuntimeException {
    protected String message;

    public BaseRuntimeException() {
    }

    public BaseRuntimeException(String message) {
        super(message);
    }
}
