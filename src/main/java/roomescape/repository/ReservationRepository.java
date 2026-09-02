package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final List<Reservation> reservations = new ArrayList<>(List.of(
            new Reservation(1L, "브라운", LocalDate.now().plusDays(1), LocalTime.of(10, 0)),
            new Reservation(2L, "브라운", LocalDate.now().plusDays(2), LocalTime.of(11, 0)),
            new Reservation(3L, "브라운", LocalDate.now().plusDays(3), LocalTime.of(12, 0))
    ));

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation ORDER BY id",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        rs.getObject("time", LocalTime.class)
                )
        );
    }

    public Reservation save(Reservation reservation) {
        Map<String, Object> parameters = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time", reservation.getTime()
        );

        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        Reservation savedReservation = new Reservation(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );

        return savedReservation;
    }

    public boolean deleteById(Long id) {
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    public boolean existsByNameAndDateAndTime(String name, LocalDate date, LocalTime time) {
        return reservations.stream()
                .anyMatch(reservation ->
                        reservation.getName().equals(name)
                                && reservation.getDate().equals(date)
                                && reservation.getTime().equals(time)
                );
    }
}
