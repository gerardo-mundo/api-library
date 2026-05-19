package mundo.org.apilibrary.payload;

public record ApiResponse<T>(
        boolean error,
        String message,
        T data
) {
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(false , message, data);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(true, message, null);
    }
}
