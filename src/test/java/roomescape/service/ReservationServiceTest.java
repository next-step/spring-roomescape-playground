package roomescape.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.TestClockConfig;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationCreateCommand;
import roomescape.exception.ReservationErrorCode;
import roomescape.exception.ReservationException;
import roomescape.repository.JdbcReservationRepository;
import roomescape.repository.JdbcTimeRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import({ReservationService.class, JdbcReservationRepository.class, JdbcTimeRepository.class, TestClockConfig.class})
class ReservationServiceTest {
    private static final LocalDate TEST_DATE = LocalDate.of(2027, 8, 4);
    private static final LocalTime RESERVATION_TIME = LocalTime.of(10, 0);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private ReservationService reservationService;

    @Test
    void 유효한_예약_요청이면_저장된_예약을_반환한다() {
        // given
        Time time = saveTime();
        ReservationCreateCommand command = new ReservationCreateCommand(
                "브라운",
                TEST_DATE.plusDays(1),
                time.getId()
        );

        // when
        Reservation reservation = reservationService.createReservation(command);

        // then
        assertThat(reservation.getId()).isNotNull();
        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo(command.date());
        assertThat(reservation.getTime().getId()).isEqualTo(time.getId());
        assertThat(reservation.getTime().getStartAt()).isEqualTo(time.getStartAt());
    }

    @Test
    void 이미_예약된_날짜와_시간이면_예외를_던진다() {
        // given
        Time time = saveTime();
        ReservationCreateCommand firstCommand = new ReservationCreateCommand(
                "브라운",
                TEST_DATE.plusDays(1),
                time.getId()
        );

        ReservationCreateCommand duplicatedCommand = new ReservationCreateCommand(
                "철수",
                TEST_DATE.plusDays(1),
                time.getId()
        );

        reservationService.createReservation(firstCommand);

        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(duplicatedCommand))
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_CONFLICT)
                );
    }

    @Test
    void 과거_날짜로_예약하면_예외를_던진다() {
        // given
        Time time = saveTime();
        ReservationCreateCommand pastDateCommand = new ReservationCreateCommand(
                "브라운",
                TEST_DATE.minusDays(1),
                time.getId()
        );

        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(pastDateCommand))
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_IN_PAST)
                );
    }

    @Test
    void 오늘_날짜의_지난_시간으로_예약하면_예외를_던진다() {
        // given
        Time time = saveTime();
        ReservationCreateCommand pastTimeCommand = new ReservationCreateCommand(
                "브라운",
                TEST_DATE,
                time.getId()
        );

        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(pastTimeCommand))
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_IN_PAST)
                );
    }

    @Test
    void 존재하는_id로_예약을_삭제한다() {
        // given
        Time time = saveTime();
        ReservationCreateCommand command = new ReservationCreateCommand(
                "브라운",
                TEST_DATE.plusDays(1),
                time.getId()
        );
        Reservation reservation = reservationService.createReservation(command);

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

    private Time saveTime() {
        return timeRepository.save(new Time(RESERVATION_TIME));
    }

}
