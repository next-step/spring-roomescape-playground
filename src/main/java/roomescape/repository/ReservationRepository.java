package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                        "FROM reservation as r inner join time as t on r.time_id = t.id", reservationRowMapper());
    }

    public Optional<Reservation> findWithId(Long id) {
        return jdbcTemplate.query(
                        "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                                "FROM reservation as r inner join time as t on r.time_id = t.id " +
                                "WHERE r.id = ?", reservationRowMapper(), id)
                .stream().findFirst();
    }

    public boolean existsByTimeId(Long timeId) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE time_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, timeId);
        return count > 0;
    }

    public Reservation insert(Reservation reservation) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId());
        Long key = simpleJdbcInsert.executeAndReturnKey(source).longValue();
        return Reservation.withId(reservation, key);
    }

    public void delete(Reservation reservation) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", reservation.getId());
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> {
            Time time = Time.withId(
                    resultSet.getLong("time_id"),
                    Time.from(LocalTime.parse(resultSet.getString("time_value")))
            );

            return Reservation.withId(
                    Reservation.restore(
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            time
                    ),
                    resultSet.getLong("reservation_id")
            );
        };
    }
}
