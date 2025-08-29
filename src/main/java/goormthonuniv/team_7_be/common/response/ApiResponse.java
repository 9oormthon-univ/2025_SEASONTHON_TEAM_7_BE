package goormthonuniv.team_7_be.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@JsonPropertyOrder({"success", "message", "data"})
public class ApiResponse<T> {

    private boolean success;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL) // NULL인 경우 제외
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.of(true, "요청에 성공하였습니다.", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.of(true, message, data);
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.of(true, "요청에 성공했습니다.", null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.of(false, message, null);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return ApiResponse.of(false, message, data);
    }
}