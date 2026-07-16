package com.deckbound.tracker.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " com id " + id + " não encontrado.");
    }
    public ResourceNotFoundException(String resource, UUID id) {
        super(resource + " com id " + id + " não encontrado.");
    }
    public ResourceNotFoundException(String resource, String name) {
        super(resource + " com id " + name + " não encontrado.");
    }
}
