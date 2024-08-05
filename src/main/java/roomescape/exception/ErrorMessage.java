package roomescape.exception;

public enum ErrorMessage {
  NO_RESERVATION("[ERROR] 예약이 존재하지 않습니다."),
  INVALID_ARGUMENT("[ERROR] 잘못된 입력입니다."),
  EMPTY_NAME("[ERROR] 이름이 비어 있습니다."),
  EMPTY_DATE("[ERROR] 날짜가 비어 있습니다."),
  EMPTY_TIME("[ERROR] 시간이 비어 있습니다.");

  private final String message;

  ErrorMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
