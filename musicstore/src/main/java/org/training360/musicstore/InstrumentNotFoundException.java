package org.training360.musicstore;

public class InstrumentNotFoundException extends RuntimeException {
    public InstrumentNotFoundException(String message) {
        super(message);
    }
}
