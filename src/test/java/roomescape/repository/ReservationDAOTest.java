package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.mapper.ReservationRowMapper;

@JdbcTest
public class ReservationDAOTest {
    private ReservationDAO reservationDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Time time;

    @BeforeEach
    void setUp() {
        reservationDAO = new ReservationDAO(jdbcTemplate, new ReservationRowMapper());

        jdbcTemplate.update("delete from reservation");
        jdbcTemplate.update("delete from time");

        jdbcTemplate.update("insert into time (id, time) values (?, ?)", 1L, LocalTime.of(15, 0));
        time = new Time(1L, LocalTime.of(15, 0));
    }

    @Test
    void 예약을_생성할_수_있다() {
        //given
        Reservation reservation = new Reservation("파도", LocalDate.now().plusDays(1), time);

        //when
        Reservation response = reservationDAO.createReservation(reservation);

        //then
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getName()).isEqualTo("파도"),
            () -> assertThat(response.getDate()).isEqualTo(LocalDate.now().plusDays(1)),
            () -> assertThat(response.getTime()).isEqualTo(time)
        );
    }

    @Test
    void 예약을_조회할_수_있다() {
        //given
        Reservation reservation = new Reservation("콜리", LocalDate.now().plusDays(1), time);
        Reservation response = reservationDAO.createReservation(reservation);

        //when
        List<Reservation> reservations = reservationDAO.findReservations();
        Reservation savedReservation = reservations.get(0);

        //then
        assertAll(
            () -> assertThat(savedReservation.getId()).isEqualTo(response.getId()),
            () -> assertThat(savedReservation.getName()).isEqualTo("콜리"),
            () -> assertThat(savedReservation.getDate()).isEqualTo(LocalDate.now().plusDays(1)),
            () -> assertThat(savedReservation.getTime()).isEqualTo(time)
        );
    }

    @Test
    void 예약을_삭제할_수_있다() {
        //given
        Reservation reservation1 = new Reservation("파도", LocalDate.now().plusDays(1), time);
        Reservation reservation2 = new Reservation("콜리", LocalDate.now().plusDays(2), time);
        Reservation response1 = reservationDAO.createReservation(reservation1);
        Reservation response2 = reservationDAO.createReservation(reservation2);

        //when
        reservationDAO.deleteReservation(response1.getId());
        List<Reservation> reservations = reservationDAO.findReservations();

        //then
        assertAll(
            () -> assertThat(reservations).hasSize(1),
            () -> assertThat(reservations.get(0).getName()).isEqualTo("콜리")
        );
    }
}
