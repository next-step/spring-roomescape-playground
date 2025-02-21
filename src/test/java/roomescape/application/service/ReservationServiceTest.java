package roomescape.application.service;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.application.dto.CreateReservationRequestDto;
import roomescape.common.error.exception.EntityNotFoundException;
import roomescape.domain.reservation.Reservation;
import roomescape.fake.FakeReservationRepository;
import roomescape.repository.reservation.interfaces.ReservationRepository;

class ReservationServiceTest {

    private ReservationService reservationService;
    private ReservationRepository reservationRepository;

    @BeforeEach
    void init() {
        reservationService = new ReservationService(new FakeReservationRepository());
    }

    @Test
    void 예약_생성_조회_테스트() {
        // given
        Reservation createdReservation = createdReservation();
        // when
        Reservation foundReservation = reservationService.findByIdOrThrow(createdReservation.getId());
        // then
        Assertions.assertThat(foundReservation.getName()).isEqualTo(createdReservation.getName());
    }

    @Test
    void 예약_삭제_테스트() {
        // given
        Reservation createdReservation = createdReservation();
        reservationService.deleteReservation(createdReservation.getId());
        // when
        Throwable catchThrowable = Assertions.catchThrowable(
                () -> reservationService.findByIdOrThrow(createdReservation.getId()));
        // then
        Assertions.assertThat(catchThrowable).isInstanceOf(EntityNotFoundException.class);
    }

    private Reservation createdReservation() {
        CreateReservationRequestDto request =
                new CreateReservationRequestDto("곰곰", LocalDate.now().plusDays(1), LocalTime.now());
        return reservationService.createReservation(request);
    }
}
