package roomescape.common.error;

public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "Invalid input value"),
    ENTITY_NOT_FOUND(404, "C002", "Entity not found"),

    // Reservation
    INVALID_RESERVE_VALUE(400, "R001", "Invalid reserve value"),
    ;

    private final int status;
    private final String code;
    private final String description;

    ErrorCode(int status, String code, String description) {
        this.status = status;
        this.code = code;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
