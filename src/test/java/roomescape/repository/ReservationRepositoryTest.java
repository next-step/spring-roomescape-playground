package roomescape.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Import(JdbcTemplateReservationRepository.class)
class ReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationRepository reservationRepository;

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("TRUNCATE TABLE RESERVATION RESTART IDENTITY");
    }

    @Test
    void 예약을_생성한다() {
        // given
        Reservation reservation = new Reservation("김철수", LocalDate.of(2025, 2, 19), LocalTime.of(13, 0));
        // when
        Reservation savedReservation = reservationRepository.save(reservation);
        // then
        assertAll(
                () -> assertThat(savedReservation.getId()).isEqualTo(1L),
                () -> assertThat(savedReservation.getName()).isEqualTo("김철수"),
                () -> assertThat(savedReservation.getDate()).isEqualTo("2025-02-19"),
                () -> assertThat(savedReservation.getTime()).isEqualTo("13:00")
        );
    }

    @Test
    void 아이디를_통해_특정_예약을_조회한다() {
        // given
        Reservation reservation = new Reservation("김철수", LocalDate.of(2025, 2, 19), LocalTime.of(13, 0));
        Reservation savedReservation = reservationRepository.save(reservation);
        // when
        Optional<Reservation> foundReservation = reservationRepository.findById(savedReservation.getId());
        // then
        assertAll(
                () -> assertThat(foundReservation).isPresent(),
                () -> assertThat(savedReservation.getId()).isEqualTo(1L),
                () -> assertThat(savedReservation.getName()).isEqualTo("김철수"),
                () -> assertThat(savedReservation.getDate()).isEqualTo("2025-02-19"),
                () -> assertThat(savedReservation.getTime()).isEqualTo("13:00")
        );
    }

    @Test
    void 모든_예약을_조회한다() {
        // given
        Reservation reservation1 = new Reservation(1L, "김철수", LocalDate.of(2025, 2, 19), LocalTime.of(13, 0));
        Reservation reservation2 = new Reservation(2L, "김영희", LocalDate.of(2025, 2, 19), LocalTime.of(14, 0));
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        // when
        List<Reservation> reservations = reservationRepository.findAll();
        // then
        assertThat(reservations).hasSize(2);
    }

    @Test
    void 해당_날짜_및_시간에_예약이_존재하면_true를_반환한다() {
        // given
        LocalDate date = LocalDate.of(2025, 2, 19);
        LocalTime time = LocalTime.of(13, 0);
        Reservation reservation = new Reservation(1L, "김철수", date, time);
        reservationRepository.save(reservation);
        // when
        boolean exists = reservationRepository.existsByDateAndTime(date, time);
        // then
        assertThat(exists).isTrue();
    }

    @Test
    void 아이디를_통해_특정_예약을_삭제한다() {
        // given
        Reservation reservation = new Reservation(1L, "김철수", LocalDate.of(2025, 2, 19), LocalTime.of(13, 0));
        Reservation savedReservation = reservationRepository.save(reservation);
        // when
        reservationRepository.deleteById(savedReservation.getId());
        // then
        Optional<Reservation> foundReservation = reservationRepository.findById(savedReservation.getId());
        assertThat(foundReservation).isEmpty();
    }
}
