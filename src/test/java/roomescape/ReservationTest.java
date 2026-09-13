package roomescape;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    @Test
    void 예약_날짜를_LocalDate로_관리한다() {
        ReservationTime time =
                new ReservationTime(1L, LocalTime.of(10, 0));

        Reservation reservation = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2026, 9, 6),
                time
        );

        assertThat(reservation.getDate())
                .isEqualTo(LocalDate.of(2026, 9, 6));
    }

    @Test
    void 날짜가_없으면_예약을_생성할_수_없다() {
        ReservationTime time =
                new ReservationTime(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> new Reservation(
                1L,
                "브라운",
                null,
                time
        ))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약 날짜는 필수입니다.");
    }

    @Test
    void 응답의_날짜_문자열_형식을_유지한다() {
        ReservationTime time =
                new ReservationTime(1L, LocalTime.of(10, 0));

        Reservation reservation = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2026, 9, 6),
                time
        );

        ReservationResponse response =
                ReservationResponse.from(reservation);

        assertThat(response.getDate()).isEqualTo("2026-09-06");
    }
}
