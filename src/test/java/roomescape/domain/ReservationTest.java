package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.exception.InvalidReservationException;

class ReservationTest {

    private static final LocalDate DATE = LocalDate.of(2026, 8, 13);
    private static final ReservationTime TIME = ReservationTime.restore(1L, LocalTime.of(15, 40));
    private static final LocalDateTime NOW = LocalDateTime.of(2026, 8, 12, 12, 0);

    @Test
    @DisplayName("예약 정보가 유효하면 예약을 생성한다")
    void createsReservationWhenDetailsAreValid() {
        Reservation reservation = Reservation.create("브라운", DATE, TIME, NOW);

        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo(DATE);
        assertThat(reservation.getTime()).isEqualTo(TIME);
    }

    @Test
    @DisplayName("예약 이름은 공백일 수 없다")
    void rejectsBlankReservationName() {
        assertThatThrownBy(() -> Reservation.create(" ", DATE, TIME, NOW))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("예약자 이름은 필수입니다.");
    }

    @Test
    @DisplayName("예약 날짜는 필수이다")
    void rejectsNullReservationDate() {
        assertThatThrownBy(() -> Reservation.create("브라운", null, TIME, NOW))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("예약 날짜는 필수입니다.");
    }

    @Test
    @DisplayName("예약 시간은 필수이다")
    void rejectsNullReservationTime() {
        assertThatThrownBy(() -> Reservation.create("브라운", DATE, null, NOW))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("예약 시간은 필수입니다.");
    }

    @Test
    @DisplayName("저장된 예약을 식별자와 함께 복원한다")
    void restoresReservationWithId() {
        Reservation restoredReservation = Reservation.restore(1L, "브라운", DATE, TIME);

        assertThat(restoredReservation.getId()).isEqualTo(1L);
        assertThat(restoredReservation.getName()).isEqualTo("브라운");
    }

    @Test
    @DisplayName("지난 일시로는 예약할 수 없다")
    void rejectsPastReservation() {
        LocalDateTime now = LocalDateTime.of(2026, 8, 13, 15, 41);

        assertThatThrownBy(() -> Reservation.create("브라운", DATE, TIME, now))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("지난 일시로는 예약할 수 없습니다.");
    }

    @Test
    @DisplayName("현재 일시에는 예약할 수 있다")
    void createsReservationAtCurrentDateTime() {
        LocalDateTime now = LocalDateTime.of(2026, 8, 13, 15, 40);

        Reservation reservation = Reservation.create("브라운", DATE, TIME, now);

        assertThat(reservation.getDate()).isEqualTo(DATE);
        assertThat(reservation.getTime()).isEqualTo(TIME);
    }
}
