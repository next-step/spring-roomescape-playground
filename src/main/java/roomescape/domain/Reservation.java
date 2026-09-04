package roomescape.domain;

import java.time.LocalDate;

import roomescape.exception.BlankReservationException;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {

  public Reservation {
    validate(name, date, time);
  }

  private static void validate(String name, LocalDate date, ReservationTime time) {
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
