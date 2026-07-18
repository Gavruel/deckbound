package com.deckbound.tracker.exception;

public class PlaygroupAccessDeniedException extends RuntimeException {
    public PlaygroupAccessDeniedException() {
        super("Você não tem acesso a este playgroup.");
    }
}
