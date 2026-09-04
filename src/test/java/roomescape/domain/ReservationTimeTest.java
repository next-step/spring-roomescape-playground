package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.exception.InvalidReservationTimeException;

class ReservationTimeTest {

    @Test
    @DisplayName("예약 시간을 생성한다")
    void createsReservationTime() {
        ReservationTime reservationTime = ReservationTime.create(LocalTime.of(15, 40));

        assertThat(reservationTime.getTime()).isEqualTo(LocalTime.of(15, 40));
    }

    @Test
    @DisplayName("시간 없이 예약 시간을 생성할 수 없다")
    void rejectsNullTime() {
        assertThatThrownBy(() -> ReservationTime.create(null))
                .isInstanceOf(InvalidReservationTimeException.class)
                .hasMessage("시간은 필수입니다.");
    }

    @Test
    @DisplayName("저장된 예약 시간을 식별자와 함께 복원한다")
    void restoresReservationTime() {
        ReservationTime reservationTime = ReservationTime.restore(1L, LocalTime.of(15, 40));

        assertThat(reservationTime.getId()).isEqualTo(1L);
        assertThat(reservationTime.getTime()).isEqualTo(LocalTime.of(15, 40));
    }

    @Test
    @DisplayName("식별자 없이 예약 시간을 복원할 수 없다")
    void rejectsNullIdWhenRestoring() {
        assertThatThrownBy(() -> ReservationTime.restore(null, LocalTime.of(15, 40)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간 ID는 null일 수 없습니다.");
    }
}
