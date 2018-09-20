package template.wrapper.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionHelper extends Exception{
    private final Logger log = LoggerFactory.getLogger(getClass());

    public ExceptionHelper(String message) {
        super(message);
        log.warn(message);
    }

    public ExceptionHelper(String message, Throwable cause) {
        super(message, cause);
        log.warn(message, cause);
    }
}
