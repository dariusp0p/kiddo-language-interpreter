package exceptions;

public class StatementException extends KiddoException {
    public StatementException(String message) {
        super(message);
    }

    public StatementException(String message, Throwable cause) {
        super(message, cause);
    }
}
