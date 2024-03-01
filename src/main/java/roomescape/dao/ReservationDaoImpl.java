package roomescape.dao;

import java.util.List;
import java.util.Objects;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
public class ReservationDaoImpl implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) -> {
        Time time = new Time(
            rs.getLong("time_id"),
            rs.getTime("time_value").toLocalTime()
        );

        return new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            time
        );
    };

    public ReservationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource()))
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> readAll() {
        return jdbcTemplate.query(
            """
                SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.time as time_value
                FROM reservation as r inner join time as t on r.time_id = t.id
                """,
            RESERVATION_ROW_MAPPER
        );
    }

    @Override
    public Reservation read(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                """
                SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.time as time_value
                FROM reservation as r inner join time as t on r.time_id = t.id
                WHERE r.id = ?
                """,
                RESERVATION_ROW_MAPPER,
                id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Reservation create(Reservation reservation) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(reservation);
        Number key = simpleJdbcInsert.executeAndReturnKey(source);

        return new Reservation(key.longValue(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

}
