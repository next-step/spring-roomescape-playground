package roomescape.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Import({ReservationRepository.class, TimeRepository.class})
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Test
    void 예약을_생성한다() {
        // given
        final Time time = new Time(LocalTime.of(13, 0));
        final Time savedTime = timeRepository.save(time);
        final Reservation reservation = new Reservation("김철수", LocalDate.of(2025, 2, 19), savedTime);
        // when
        final Reservation savedReservation = reservationRepository.save(reservation);
        // then
        assertAll(
                () -> assertThat(savedReservation.getId()).isNotNull(),
                () -> assertThat(savedReservation.getName()).isEqualTo("김철수"),
                () -> assertThat(savedReservation.getDate()).isEqualTo("2025-02-19"),
                () -> assertThat(savedReservation.getTime().getTime()).isEqualTo("13:00")
        );
    }

    @Test
    void 아이디를_통해_특정_예약을_조회한다() {
        // given
        final Time time = new Time(LocalTime.of(13, 0));
        final Time savedTime = timeRepository.save(time);
        final Reservation reservation = new Reservation("김철수", LocalDate.of(2025, 2, 19), savedTime);
        final Reservation savedReservation = reservationRepository.save(reservation);
        // when
        final Optional<Reservation> foundReservation = reservationRepository.findById(savedReservation.getId());
        // then
        assertThat(foundReservation)
                .hasValueSatisfying(reservationResult -> assertAll(
                        () -> assertThat(reservationResult.getId()).isEqualTo(savedReservation.getId()),
                        () -> assertThat(reservationResult.getName()).isEqualTo(savedReservation.getName()),
                        () -> assertThat(reservationResult.getDate()).isEqualTo(savedReservation.getDate()),
                        () -> assertThat(reservationResult.getTime().getId()).isEqualTo(savedReservation.getTime().getId()),
                        () -> assertThat(reservationResult.getTime().getTime()).isEqualTo(savedReservation.getTime().getTime())
                ));
    }

    @Test
    void 모든_예약을_조회한다() {
        // given
        final Time time1 = new Time(LocalTime.of(13, 0));
        final Time time2 = new Time(LocalTime.of(14, 0));
        final Time savedTime1 = timeRepository.save(time1);
        final Time savedTime2 = timeRepository.save(time2);
        final Reservation reservation1 = new Reservation("김철수", LocalDate.of(2025, 2, 19), savedTime1);
        final Reservation reservation2 = new Reservation("김영희", LocalDate.of(2025, 2, 19), savedTime2);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        // when
        final List<Reservation> reservations = reservationRepository.findAll();
        // then
        assertThat(reservations).hasSize(2);
    }

    @Test
    void 해당_날짜_및_시간에_예약이_존재하면_true를_반환한다() {
        // given
        final LocalDate date = LocalDate.of(2025, 2, 19);
        final Time time = new Time(LocalTime.of(13, 0));
        final Time savedTime = timeRepository.save(time);
        final Reservation reservation = new Reservation("김철수", date, savedTime);
        reservationRepository.save(reservation);
        // when
        final boolean exists = reservationRepository.existsByDateAndTime(date, time.getTime());
        // then
        assertThat(exists).isTrue();
    }

    @Test
    void 아이디를_통해_특정_예약을_삭제한다() {
        // given
        final Time time = new Time(LocalTime.of(13, 0));
        final Time savedTime = timeRepository.save(time);
        final Reservation reservation = new Reservation("김철수", LocalDate.of(2025, 2, 19), savedTime);
        final Reservation savedReservation = reservationRepository.save(reservation);
        // when
        reservationRepository.deleteById(savedReservation.getId());
        // then
        final Optional<Reservation> foundReservation = reservationRepository.findById(savedReservation.getId());
        assertThat(foundReservation).isEmpty();
    }
}
