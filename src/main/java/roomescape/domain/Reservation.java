package roomescape.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

  private final Long id;

  @NotBlank(message = "ErrorMessage.EMPTY_NAME.getMessage()")
  private final String name;

  @NotNull(message = "ErrorMessage.EMPTY_DATE.getMessage()")
  private final LocalDate date;

  @NotNull(message = "ErrorMessage.EMPTY_TIME.getMessage()")
  private final LocalTime time;

  public Reservation(Long id, String name, LocalDate date, LocalTime time) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = time;
  }

  public static Reservation of(Long id, Reservation reservation) {
    return new Reservation(id, reservation.name, reservation.date, reservation.time);
  }

  public Long getId() {
    return id;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Reservation reservation)) {
      return false;
    }
    return Objects.equals(id, reservation.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
