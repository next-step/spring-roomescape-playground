package roomescape.common;

public enum ExceptionMessage {
    BAD_REQUEST("잘못된 요청입니다."),
    BAD_REQUEST_REQUEST_BODY_VALID("잘못된 요청본문입니다."),
    BAD_REQUEST_REQUEST_FOR_NON_EXISTENT_DATA("존재하지 않는 정보에 대해 요청을 하였습니다."),
    BAD_REQUEST_REQUEST_FOR_DUPLICATE_CREATION_OF_UNIQUE_DATA("중복이 허용되지 않는 정보를 중복으로 저장하려 하였습니다."),
    BAD_REQUEST_MISSING_PARAM("필수 파라미터가 없습니다."),

    BAD_TIME_FORMAT("시는 00~23, 분은 00~60 사이의 숫자로 표현해야 합니다."),
    BAD_TIME_RANGE("시간은 hh:mm 형식이여야 합니다.");

    private final String message;

    ExceptionMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
