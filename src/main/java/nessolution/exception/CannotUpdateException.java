package nessolution.exception;

public class CannotUpdateException extends RuntimeException{
    public CannotUpdateException() {
    }

    public CannotUpdateException(String message) {
        super(message);
    }

    public CannotUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotUpdateException(Throwable cause) {
        super(cause);
    }

    public CannotUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
