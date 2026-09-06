package roomescape;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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
        String sql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> mapReservation(resultSet)
        );
    }

    public Reservation saveReservation(
            String name,
            LocalDate date,
            LocalTime time
    ) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("date", date.toString())
                .addValue("time", time.toString());

        Number id = simpleJdbcInsert.executeAndReturnKey(params);

        return new Reservation(
                id.longValue(),
                name,
                date,
                time
        );
    }

    public Optional<Reservation> getReservation(long id) {
        String sql = "SELECT id, name, date, time FROM reservation WHERE id = ?";

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
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        );
    }
}
