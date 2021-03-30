package nessolution.exception;

public class CannotDeleteException extends RuntimeException {
    public CannotDeleteException() {
        super();
    }

    public CannotDeleteException(String message) {
        super(message);
    }

    public CannotDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotDeleteException(Throwable cause) {
        super(cause);
    }

    protected CannotDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
