package roomescape;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.BlankReservationException;

public class ReservationTest {

  @Test
  @DisplayName("name이 null이면 예외를 던진다.")
  void name이_null이면_예외를_던진다() {
    BlankReservationException exception = assertThrows(BlankReservationException.class,
        () -> new Reservation(1L, null, LocalDate.now(), new ReservationTime(1L, LocalTime.now())));

    assertEquals("이름은 공백이 될 수 없습니다.", exception.getMessage());
  }

  @Test
  @DisplayName("name이 빈 문자열이면 예외를 던진다.")
  void name이_빈_문자열이면_예외를_던진다() {
    BlankReservationException exception = assertThrows(BlankReservationException.class,
        () -> new Reservation(1L, "", LocalDate.now(), new ReservationTime(1L, LocalTime.now())));

    assertEquals("이름은 공백이 될 수 없습니다.", exception.getMessage());
  }

  @Test
  @DisplayName("date가 null이면 예외를 던진다.")
  void date가_null이면_예외를_던진다() {
    BlankReservationException exception = assertThrows(BlankReservationException.class,
        () -> new Reservation(1L, "브라운", null, new ReservationTime(1L, LocalTime.now())));

    assertEquals("예약일자는 공백이 될 수 없습니다.", exception.getMessage());
  }

  @Test
  @DisplayName("time이 null이면 예외를 던진다.")
  void time이_null이면_예외를_던진다() {
    BlankReservationException exception = assertThrows(BlankReservationException.class,
        () -> new Reservation(1L, "브라운", LocalDate.now(), null));

    assertEquals("예약시간은 공백이 될 수 없습니다.", exception.getMessage());
  }

  @Test
  @DisplayName("유효한 값이 주어지면 예외 없이 예약이 생성된다.")
  void 유효한_값이면_예약이_생성된다() {
    LocalDate date = LocalDate.now();
    ReservationTime time = new ReservationTime(1L, LocalTime.now());

    Reservation reservation = assertDoesNotThrow(() -> new Reservation(1L, "브라운", date, time));

    assertAll(
        () -> assertEquals(1L, reservation.getId()),
        () -> assertEquals("브라운", reservation.getName()),
        () -> assertEquals(date, reservation.getDate()),
        () -> assertEquals(time, reservation.getTime())
    );
  }
}
