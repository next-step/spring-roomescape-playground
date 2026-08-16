package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateCommand;
import roomescape.exception.ReservationErrorCode;
import roomescape.exception.ReservationException;
import roomescape.repository.InMemoryReservationRepository;
import roomescape.repository.ReservationRepository;
import roomescape.service.ReservationService;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationServiceTest {
    private static final LocalDate TODAY = LocalDate.of(2027, 8, 14);
    private static final LocalTime CURRENT_TIME = LocalTime.of(12, 0);
    private static final LocalTime RESERVATION_TIME = LocalTime.of(10, 0);
    private static final Clock CLOCK = fixedClockAt(TODAY, CURRENT_TIME);

    private ReservationRepository reservationRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = new InMemoryReservationRepository();
        reservationService = new ReservationService(reservationRepository, CLOCK);
    }

    @Test
    void 유효한_예약_요청이면_저장된_예약을_반환한다() {
        // given
        ReservationCreateCommand command = new ReservationCreateCommand(
                "브라운",
                TODAY.plusDays(1),
                RESERVATION_TIME
        );

        // when
        Reservation reservation = reservationService.addReservation(command);

        // then
        assertThat(reservation.getId()).isNotNull();
        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo(command.date());
        assertThat(reservation.getTime()).isEqualTo(command.time());
    }

    @Test
    void 이미_예약된_날짜와_시간이면_예외를_던진다() {
        // given
        ReservationCreateCommand firstCommand = new ReservationCreateCommand(
                "브라운",
                TODAY.plusDays(1),
                RESERVATION_TIME
        );

        ReservationCreateCommand duplicatedCommand = new ReservationCreateCommand(
                "철수",
                TODAY.plusDays(1),
                RESERVATION_TIME
        );

        reservationService.addReservation(firstCommand);

        // when & then
        assertThatThrownBy(() -> reservationService.addReservation(duplicatedCommand))
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_CONFLICT)
                );
    }

    @Test
    void 과거_날짜로_예약하면_예외를_던진다() {
        // given
        ReservationCreateCommand pastDateCommand = new ReservationCreateCommand(
                "브라운",
                TODAY.minusDays(1),
                RESERVATION_TIME
        );

        // when & then
        assertThatThrownBy(() -> reservationService.addReservation(pastDateCommand))
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_IN_PAST)
                );
    }

    @Test
    void 오늘_날짜의_지난_시간으로_예약하면_예외를_던진다() {
        // given
        ReservationCreateCommand pastTimeCommand = new ReservationCreateCommand(
                "브라운",
                TODAY,
                RESERVATION_TIME
        );

        // when & then
        assertThatThrownBy(() -> reservationService.addReservation(pastTimeCommand))
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_IN_PAST)
                );
    }

    @Test
    void 존재하는_id로_예약을_삭제한다() {
        // given
        ReservationCreateCommand command = new ReservationCreateCommand(
                "브라운",
                TODAY.plusDays(1),
                RESERVATION_TIME
        );
        Reservation reservation = reservationService.addReservation(command);

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
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_NOT_FOUND)
                );
    }

    private static Clock fixedClockAt(LocalDate date, LocalTime time) {
        return Clock.fixed(
                LocalDateTime.of(date, time)
                        .atZone(ZoneId.systemDefault())
                        .toInstant(),
                ZoneId.systemDefault()
        );
    }
}
