package roomescape;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationsTest {

    @Test
    void 예약을_추가할_수_있다() {
        //given
        Reservations reservations = new Reservations();
        Reservation 예약 = new Reservation("가영", "2024-04-28", "12:00");

        //when
        reservations.register(예약);

        //then
        assertThat(reservations.getReservations()).contains(예약);
    }

    @Test
    void 예약을_삭제할_수_있다() {
        //given
        Reservations reservations = new Reservations();
        Reservation 예약 = new Reservation("가영", "2024-04-28", "12:00");
        reservations.register(예약);

        //when
        reservations.cancel(예약.getId());

        //then
        assertThat(reservations.getReservations()).doesNotContain(예약);
    }

    @Test
    void 모든_예약을_조회_할_수_있다() {
        //given
        Reservations reservations = new Reservations();
        Reservation 예약1 = new Reservation("가영", "2024-04-28", "12:00");
        Reservation 예약2 = new Reservation("나영", "2024-04-29", "13:00");
        reservations.register(예약1);
        reservations.register(예약2);

        //when
        List<Reservation> reservationList = reservations.getReservations();

        //then
        assertThat(reservationList).contains(예약1, 예약2);
    }

}
