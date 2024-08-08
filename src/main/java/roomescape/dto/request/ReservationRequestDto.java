package roomescape.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequestDto {

  @NotBlank(message = "ErrorMessage.EMPTY_NAME.getMessage()")
  private String name;

  @NotNull(message = "ErrorMessage.EMPTY_DATE.getMessage()")
  private LocalDate date;

  @NotNull(message = "ErrorMessage.EMPTY_TIME.getMessage()")
  private LocalTime time;

  public ReservationRequestDto(String name, LocalDate date, LocalTime time) {
    this.name = name;
    this.date = date;
    this.time = time;
  }

  public String getName() {
    return name;
  }

  public LocalDate getDate() {
    return date;
  }

  public LocalTime getTime() {
    return time;
  }
}
