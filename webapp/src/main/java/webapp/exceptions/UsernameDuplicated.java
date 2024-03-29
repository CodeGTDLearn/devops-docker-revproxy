package webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameDuplicated extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public UsernameDuplicated(String message) {
        super(message);
    }
}
