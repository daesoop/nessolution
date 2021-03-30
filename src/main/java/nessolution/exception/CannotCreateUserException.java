package nessolution.exception;

public class CannotCreateUserException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CannotCreateUserException(String message) {
        super(message);
    }
}
