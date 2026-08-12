package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationServiceTest {

    private ReservationRepository reservationRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = new InMemoryReservationRepository();
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    void 유효한_예약_요청이면_저장된_예약을_반환한다() {
        // given
        ReservationRequest request = new ReservationRequest(
                LocalDate.now().plusDays(1),
                "브라운",
                LocalTime.of(10, 0)
        );

        // when
        Reservation reservation = reservationService.addReservation(request);

        // then
        assertThat(reservation.getId()).isNotNull();
        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo(request.date());
        assertThat(reservation.getTime()).isEqualTo(request.time());
    }

    @Test
    void 이미_예약된_날짜와_시간이면_예외를_던진다() {
        // given
        ReservationRequest firstRequest = new ReservationRequest(
                LocalDate.now().plusDays(1),
                "브라운",
                LocalTime.of(10, 0)
        );

        ReservationRequest duplicatedRequest = new ReservationRequest(
                LocalDate.now().plusDays(1),
                "철수",
                LocalTime.of(10, 0)
        );

        reservationService.addReservation(firstRequest);

        // when & then
        assertThatThrownBy(() -> reservationService.addReservation(duplicatedRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 과거_날짜로_예약하면_예외를_던진다() {
        // given
        ReservationRequest pastDateRequest = new ReservationRequest(
                LocalDate.now().minusDays(1),
                "브라운",
                LocalTime.of(10, 0)
        );

        // when & then
        assertThatThrownBy(() -> reservationService.addReservation(pastDateRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 오늘_날짜의_지난_시간으로_예약하면_예외를_던진다() {
        // given
        ReservationRequest pastTimeRequest = new ReservationRequest(
                LocalDate.now(),
                "브라운",
                LocalTime.of(0, 0)
        );

        // when & then
        assertThatThrownBy(() -> reservationService.addReservation(pastTimeRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하는_id로_예약을_삭제한다() {
        // given
        ReservationRequest request = new ReservationRequest(
                LocalDate.now().plusDays(1),
                "브라운",
                LocalTime.of(10, 0)
        );
        Reservation reservation = reservationService.addReservation(request);

        // when
        reservationService.deleteReservation(reservation.getId());

        // then
        assertThat(reservationRepository.findById(reservation.getId())).isEmpty();
    }

    @Test
    void 존재하지_않는_id로_예약_삭제_요청_시_예외를_던진다() {
        // given
        Long nonExistingId = -1L;

        // when & then
        assertThatThrownBy(() -> reservationService.deleteReservation(nonExistingId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
