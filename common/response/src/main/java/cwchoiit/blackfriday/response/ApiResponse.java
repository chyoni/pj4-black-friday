package cwchoiit.blackfriday.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean ok;
    private T data;
    private String reason;

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.ok = true;
        response.data = data;
        return response;
    }

    public static <Void> ApiResponse<Void> ok() {
        ApiResponse<Void> response = new ApiResponse<>();
        response.ok = true;
        return response;
    }

    public static <Void> ApiResponse<Void> error(String reason) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.ok = false;
        response.reason = reason;
        return response;
    }

    public static <T> ApiResponse<T> build(boolean ok, T data, String reason) {
        ApiResponse<T> response = new ApiResponse<>();
        response.ok = ok;
        response.data = data;
        response.reason = reason;
        return response;
    }
}
