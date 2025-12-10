package exceptions;

public class KiddoException extends Exception {
    public KiddoException(String message) {
        super(message);
    }

    public KiddoException(String message, Throwable cause) {
        super(message, cause);
    }
}
