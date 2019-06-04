package tk.tarajki.meme.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserAuthException extends RuntimeException {
    public UserAuthException(String message) {
        super(message);
    }
}
