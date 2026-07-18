package com.deckbound.tracker.exception;

public class AlreadyMemberException extends BusinessException {
    public AlreadyMemberException() {
        super("Member is already in playgroup");
    }
}
