package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    public List<Reservation> getReservations() {
        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.time as time_value
                FROM reservation as r inner join time as t on r.time_id = t.id
                """;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> mapReservation(resultSet)
        );
    }

    public Reservation saveReservation(
            String name,
            LocalDate date,
            Time time
    ) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("date", date.toString())
                .addValue("time_id", time.getId());

        Number id = simpleJdbcInsert.executeAndReturnKey(params);

        return new Reservation(
                id.longValue(),
                name,
                date,
                time
        );
    }

    public Optional<Reservation> getReservation(long id) {
        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.time as time_value
                FROM reservation as r
                INNER JOIN time as t ON r.time_id = t.id
                WHERE r.id = ?
                """;

        List<Reservation> reservations = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> mapReservation(resultSet),
                id
        );

        return reservations.stream().findFirst();
    }

    public int deleteReservation(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }

    private Reservation mapReservation(ResultSet resultSet) throws SQLException {

        Time time = new Time(
                resultSet.getLong("time_id"),
                LocalTime.parse(resultSet.getString("time_value"))
        );

        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                time
        );
    }
}
