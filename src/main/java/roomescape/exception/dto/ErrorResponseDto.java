package roomescape.exception.dto;

import java.time.LocalDateTime;

public class ErrorResponseDto {

  private LocalDateTime timestamp;
  private int status;
  private String error;
  private String message;
  private String path;

  public ErrorResponseDto(LocalDateTime timestamp, int status, String error, String message, String path) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
  }
}
