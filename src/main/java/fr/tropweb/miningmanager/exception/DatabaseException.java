package fr.tropweb.miningmanager.exception;

public class DatabaseException extends RuntimeException {

    public DatabaseException(final String message, final String... args) {
        super(String.format(message, args));
    }

    public DatabaseException(final Throwable throwable) {
        super(throwable);
    }
}
