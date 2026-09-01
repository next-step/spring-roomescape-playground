package roomescape.domain;

import java.time.LocalDate;

import lombok.Getter;
import roomescape.exception.BlankReservationException;

@Getter
public class Reservation {

  private final Long id;
  private final String name;
  private final LocalDate date;
  private final ReservationTime time;

  public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
    validate(name, date, time);
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = time;
  }

  public static void validate(String name, LocalDate date, ReservationTime time) {
    if (name == null || name.isBlank()) {
      throw new BlankReservationException("이름은 공백이 될 수 없습니다.");
    }
    if (date == null) {
      throw new BlankReservationException("예약일자는 공백이 될 수 없습니다.");
    }
    if (time == null) {
      throw new BlankReservationException("예약시간은 공백이 될 수 없습니다.");
    }
  }
}
