package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.exception.InvalidReservationRequestException;

class ReservationTest {

    private static final LocalDate DATE = LocalDate.of(2026, 8, 13);
    private static final ReservationTime TIME = new ReservationTime(1L, LocalTime.of(15, 40));

    @Test
    @DisplayName("예약 정보가 유효하면 예약을 생성한다")
    void createsReservationWhenDetailsAreValid() {
        Reservation reservation = new Reservation(null, "브라운", DATE, TIME);

        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo(DATE);
        assertThat(reservation.getTime()).isEqualTo(TIME);
    }

    @Test
    @DisplayName("예약 이름은 공백일 수 없다")
    void rejectsBlankReservationName() {
        assertThatThrownBy(() -> new Reservation(null, " ", DATE, TIME))
                .isInstanceOf(InvalidReservationRequestException.class);
    }

    @Test
    @DisplayName("식별자를 부여해도 기존 예약은 변경되지 않는다")
    void assignsIdWithoutChangingOriginalReservation() {
        Reservation reservation = new Reservation(null, "브라운", DATE, TIME);

        Reservation savedReservation = reservation.withId(1L);

        assertThat(reservation.getId()).isNull();
        assertThat(savedReservation.getId()).isEqualTo(1L);
        assertThat(savedReservation.getName()).isEqualTo(reservation.getName());
    }
}
