package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.exception.BlankReservationException;

public class Reservation {

  private final Long id;
  private final String name;
  private final LocalDate date;
  @JsonFormat(pattern = "HH:mm")
  private final LocalTime time;

  public Reservation(Long id, String name, LocalDate date, LocalTime time) {
    validate(name, date, time);
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = time;
  }

  public static void validate(String name, LocalDate date, LocalTime time) {
    if (name == null || name.isEmpty()) {
      throw new BlankReservationException("이름은 공백이 될 수 없습니다.");
    }
    if (date == null) {
      throw new BlankReservationException("예약일자는 공백이 될 수 없습니다.");
    }
    if (time == null) {
      throw new BlankReservationException("예약시간은 공백이 될 수 없습니다.");
    }
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
}
