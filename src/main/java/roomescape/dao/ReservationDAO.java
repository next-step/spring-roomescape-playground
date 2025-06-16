package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.Time;
import roomescape.repository.ReservationRepository;

@Repository
public class ReservationDAO implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDAO(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservations = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new Time(
                        resultSet.getLong("time_id"),
                        resultSet.getString("time_value")
                )
        );
        return reservations;
    };

    @Override
    public boolean existById(Long id) {
        String sql = "SELECT COUNT(1) FROM reservation WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count != null && count > 0;
    }

    @Override
    public Reservation findById(Long id) {
        String sql = """
                     SELECT r.id as reservation_id,
                            r.name,
                            r.date,
                            t.id as time_id,
                            t.time as time_value
                     FROM reservation as r INNER JOIN time as t on r.time_id = t.id
                     WHERE r.id = ?
                """;

        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                     SELECT r.id as reservation_id,
                            r.name,
                            r.date,
                            t.id as time_id,
                            t.time as time_value
                     FROM reservation as r INNER JOIN time as t on r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @Override
    public Time findTimeById(Long timeId) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) ->
                new Time(
                        resultSet.getLong("id"),
                        resultSet.getString("time")
                ), timeId);
    }

    @Override
    public Reservation save(Reservation reservation) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId());

        Long newId = jdbcInsert.executeAndReturnKey(params).longValue();

        return new Reservation(newId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

}
