package webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException implements Serializable{
    
    private static final long serialVersionUID = 1L;

    public ResourceNotFound(String message)
    {
        super(message);
    }
}
