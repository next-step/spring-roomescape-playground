package roomescape.exception.Time;

public enum TimeErrorMessage {

    INVALID_DATA("필요 인자가 존재하지 않습니다."),
    NOT_FOUND("해당 시간이 존재하지 않습니다.");

    private final String message;

    TimeErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
