package com.deckbound.tracker.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " com id " + id + " não encontrado.");
    }
}
