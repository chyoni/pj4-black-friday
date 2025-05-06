package cwchoiit.blackfriday.order.exception;

import cwchoiit.blackfriday.exception.BlackFridayException;
import cwchoiit.blackfriday.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OrderExceptionAdvice {

    @ExceptionHandler(BlackFridayException.class)
    public ResponseEntity<ApiResponse<Void>> handleBlackFridayException(BlackFridayException e) {
        log.error("[handleBlackFridayException] [{}] - {} : {}", e.getCode(), e.getStatus(), e.getReason(), e);
        return ResponseEntity
                .status(e.getStatus())
                .body(ApiResponse.error(e.getReason()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("[handleException] Unknown Error. ", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Something went wrong. Please try again later."));
    }
}
