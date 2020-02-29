package webapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceNotFoundDetails {

    private String title;
    private String detail;
    private String developerMessage;
    private int status;
    private long timeStamp;
}
