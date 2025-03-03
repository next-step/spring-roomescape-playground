package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepository {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) ->
    {
        final Time time = new Time(
                rs.getLong("time_id"),
                rs.getTime("time_value").toLocalTime()
        );
        return new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                time
        );
    };

    private final SimpleJdbcInsert jdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation save(final Reservation reservation) {
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId());

        final long id = jdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Reservation(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public List<Reservation> findAll() {
        final String selectAll = """
                SELECT r.id AS reservation_id,
                       r.name,
                       r.date,
                       t.id AS time_id,
                       t.time AS time_value
                FROM reservation AS r
                INNER JOIN time AS t
                ON r.time_id = t.id
                """;
        return jdbcTemplate.query(selectAll, RESERVATION_ROW_MAPPER);
    }

    public Optional<Reservation> findById(final long reservationId) {
        try {
            final String selectById = """
                    SELECT r.id AS reservation_id,
                           r.name,
                           r.date,
                           t.id AS time_id,
                           t.time AS time_value
                    FROM reservation AS r
                    INNER JOIN time AS t
                    ON r.time_id = t.id
                    WHERE r.id = ?
                    """;
            final Reservation reservation = jdbcTemplate.queryForObject(selectById, RESERVATION_ROW_MAPPER, reservationId);
            return Optional.ofNullable(reservation);
        } catch (final EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    public boolean existsByDateAndTime(final LocalDate date, final LocalTime time) {
        final String selectByDateAndTime = """
                SELECT EXISTS (
                SELECT 1
                FROM reservation AS r
                INNER JOIN time AS t
                ON r.time_id = t.id
                WHERE r.`date` = ? AND t.`time` = ?)
                """;
        return jdbcTemplate.queryForObject(selectByDateAndTime, Boolean.class, date, time);
    }

    public void deleteById(final long reservationId) {
        final String deleteById = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(deleteById, reservationId);
    }
}
