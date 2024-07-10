package roomescape.template;

public class ApiResponseTemplate<T> {

    private final String status;
    private final T data;

    public ApiResponseTemplate(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}
