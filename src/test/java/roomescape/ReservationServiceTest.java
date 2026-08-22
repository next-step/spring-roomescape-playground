package roomescape;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateCommand;
import roomescape.exception.ReservationErrorCode;
import roomescape.exception.ReservationException;
import roomescape.repository.JdbcReservationRepository;
import roomescape.repository.ReservationRepository;
import roomescape.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import({ReservationService.class, JdbcReservationRepository.class, TestClockConfig.class})
class ReservationServiceTest {
    private static final LocalDate TEST_DATE = LocalDate.of(2027, 8, 4);
    private static final LocalTime RESERVATION_TIME = LocalTime.of(10, 0);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Test
    void 유효한_예약_요청이면_저장된_예약을_반환한다() {
        // given
        ReservationCreateCommand command = new ReservationCreateCommand(
                "브라운",
                TEST_DATE.plusDays(1),
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
                TEST_DATE.plusDays(1),
                RESERVATION_TIME
        );

        ReservationCreateCommand duplicatedCommand = new ReservationCreateCommand(
                "철수",
                TEST_DATE.plusDays(1),
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
                TEST_DATE.minusDays(1),
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
                TEST_DATE,
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
                TEST_DATE.plusDays(1),
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

}
