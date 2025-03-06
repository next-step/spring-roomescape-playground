package roomescape.common.error;

public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "Invalid input value"),
    ENTITY_NOT_FOUND(404, "C002", "Entity not found"),

    // Reservation
    INVALID_RESERVE_VALUE(400, "R001", "Invalid reserve value"),

    // Time
    INVALID_TIME_VALUE(400, "T001", "시간을 다시 입력해주세요"),
    EXISTS_TIME_VALUE(400, "T002", "예약되지 않은 시간을 다시 입력해주세요."),
    // Server
    SERVER_ERROR(500, "E003", "Server error"),
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
