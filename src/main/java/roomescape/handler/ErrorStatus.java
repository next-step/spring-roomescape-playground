package roomescape.handler;

import org.springframework.http.HttpStatus;

public enum ErrorStatus {

  // Base
  _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4000"),
  _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON4003"),
  _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON4004"),

  // Input Validator
  UNEXPECTED_INPUT(HttpStatus.BAD_REQUEST, "INPUT4001"),

  // Reservation
  _NOT_FOUND_RESERVAIOTN(HttpStatus.NOT_FOUND, "RESERVATION4001"),

  //TIME
  _NOT_FOUND_TIME(HttpStatus.NOT_FOUND, "RESERVATION4001");

  private final HttpStatus status;
  private final String message;

  ErrorStatus(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  public ErrorReasonDto toErrorReasonDto() {
    return new ErrorReasonDto(status, message);
  }

}
