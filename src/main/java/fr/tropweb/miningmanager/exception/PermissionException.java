package fr.tropweb.miningmanager.exception;

public class PermissionException extends RuntimeException {

    public PermissionException(final String message, final String... args) {
        super(String.format(message, args));
    }

    public PermissionException() {
        this("You are not allowed to use this command");
    }
}
