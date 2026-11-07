package com.garage.garage.exception;

public abstract class GarageException extends RuntimeException {
    public GarageException(String message) {
        super(message);
    }
}
