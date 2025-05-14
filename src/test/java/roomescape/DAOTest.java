package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;

@SpringBootTest
public class DAOTest {

    @Autowired
    private ReservationDAO reservationDAO;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationDAO.findAll().forEach(reservation ->
                reservationDAO.deleteReservation(reservation.getId()));
    }

    @Test
    void addReservation() {
        Reservation reservation = new Reservation(1, "전서희", LocalDate.parse("2026-05-12"), LocalTime.parse("19:00"));
        reservationDAO.addReservation(reservation);
        assertThat(reservationDAO.findAll()).isNotEmpty();
    }

    @Test
    void findReservation() {
        Reservation reservation = new Reservation(1, "전서희", LocalDate.parse("2026-05-12"), LocalTime.parse("19:00"));
        reservationDAO.addReservation(reservation);

        Optional<Reservation> found = reservationDAO.findByID(1);
        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(reservation);
    }

    @Test
    void deleteReservation() {
        Reservation reservation = new Reservation(1, "전서희", LocalDate.parse("2026-05-12"), LocalTime.parse("19:00"));
        reservationDAO.addReservation(reservation);
        reservationDAO.deleteReservation(1);

        Optional<Reservation> result = reservationDAO.findByID(1);
        assertThat(result).isEmpty();
    }

    @Test
    void 오단계() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 육단계() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05", "15:40");

        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }
}
