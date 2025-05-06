package cwchoiit.blackfriday.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class BlackFridayException extends RuntimeException {
    private final HttpStatus status;
    private final String code;
    private final String reason;

    public BlackFridayException(HttpStatus status, String code, String reason) {
        super(reason);
        this.status = status;
        this.code = code;
        this.reason = reason;
    }
}
