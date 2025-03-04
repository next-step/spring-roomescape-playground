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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.mapper.ReservationRowMapper;

@JdbcTest
public class ReservationDAOTest {
    private final ReservationDAO reservationDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReservationDAOTest(@Autowired JdbcTemplate jdbcTemplate, @Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.reservationDAO = new ReservationDAO(jdbcTemplate, new ReservationRowMapper());
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private Time createTime(LocalTime time) {
        String sqlInsert = "insert into time (id, time) values (:id, :time)";
        MapSqlParameterSource paramsInsert = new MapSqlParameterSource();
        paramsInsert.addValue("id", 1L);
        paramsInsert.addValue("time", time);

        namedParameterJdbcTemplate.update(sqlInsert, paramsInsert);

        String sqlSelect = "SELECT id, time FROM time WHERE id = :id";
        MapSqlParameterSource paramsSelect = new MapSqlParameterSource();
        paramsSelect.addValue("id", 1L);

        return namedParameterJdbcTemplate.queryForObject(
            sqlSelect, paramsSelect, (rs, rowNum) -> new Time(rs.getLong("id"), rs.getTime("time").toLocalTime())
        );
    }


    @Test
    void 예약을_정상적으로_생성할_수_있다() {
        // given
        LocalTime reservationTime = LocalTime.of(15, 0);
        LocalDate reservationDate = LocalDate.now().plusDays(1);

        Time time = createTime(reservationTime);

        Reservation newReservation = new Reservation("파도", reservationDate, time);

        // when
        Reservation savedReservation = reservationDAO.createReservation(newReservation);

        // then
        assertAll(
            () -> assertThat(savedReservation.getId()).isNotNull(),
            () -> assertThat(savedReservation.getName()).isEqualTo("파도"),
            () -> assertThat(savedReservation.getDate()).isEqualTo(reservationDate),
            () -> assertThat(savedReservation.getTime()).isEqualTo(time)
        );
    }

    @Test
    void 예약을_정상적으로_조회할_수_있다() {
        // given
        LocalTime reservationTime = LocalTime.of(15, 0);
        LocalDate reservationDate = LocalDate.now().plusDays(1);

        Time time = createTime(reservationTime);

        Reservation newReservation = new Reservation("콜리", reservationDate, time);
        Reservation savedReservation = reservationDAO.createReservation(newReservation);

        // when
        List<Reservation> allReservations = reservationDAO.findReservations();
        Reservation fetchedReservation = allReservations.get(0);

        // then
        assertAll(
            () -> assertThat(fetchedReservation.getId()).isEqualTo(savedReservation.getId()),
            () -> assertThat(fetchedReservation.getName()).isEqualTo("콜리"),
            () -> assertThat(fetchedReservation.getDate()).isEqualTo(reservationDate),
            () -> assertThat(fetchedReservation.getTime()).isEqualTo(time)
        );
    }

    @Test
    void 예약을_정상적으로_삭제할_수_있다() {
        // given
        LocalTime reservationTime = LocalTime.of(15, 0);

        LocalDate reservationDate1 = LocalDate.now().plusDays(1);
        LocalDate reservationDate2 = LocalDate.now().plusDays(2);

        Time time = createTime(reservationTime);

        Reservation reservationToDelete = new Reservation("파도", reservationDate1, time);
        Reservation reservationToKeep = new Reservation("콜리", reservationDate2, time);

        Reservation savedReservation1 = reservationDAO.createReservation(reservationToDelete);
        Reservation savedReservation2 = reservationDAO.createReservation(reservationToKeep);

        // when
        reservationDAO.deleteReservation(savedReservation1.getId());
        List<Reservation> remainingReservations = reservationDAO.findReservations();

        // then
        assertAll(
            () -> assertThat(remainingReservations).hasSize(1),
            () -> assertThat(remainingReservations.get(0).getName()).isEqualTo("콜리")
        );
    }
}
