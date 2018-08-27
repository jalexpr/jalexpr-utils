package template.wrapper.exception;

public class NotValidException extends ExceptionHelper {
    public NotValidException(String message) {
        super(message);
    }

    public NotValidException(String message, Throwable ex) {
        super(message, ex);
    }
}