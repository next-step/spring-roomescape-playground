package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepository {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) ->
            new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime()
            );

    private final SimpleJdbcInsert jdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation save(final Reservation reservation) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);
        long id = jdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Reservation(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public List<Reservation> findAll() {
        String selectAll = "SELECT * FROM RESERVATION";
        return jdbcTemplate.query(selectAll, RESERVATION_ROW_MAPPER);
    }

    public Optional<Reservation> findById(final long reservationId) {
        try {
            String selectById = "SELECT * FROM RESERVATION WHERE id = ?";
            Reservation reservation = jdbcTemplate.queryForObject(selectById, RESERVATION_ROW_MAPPER, reservationId);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    public boolean existsByDateAndTime(final LocalDate date, final LocalTime time) {
        String selectByDateAndTime = "SELECT EXISTS (" +
                "SELECT 1 FROM RESERVATION WHERE `date` = ? AND `time` = ?)";
        return jdbcTemplate.queryForObject(selectByDateAndTime, Boolean.class, date, time);
    }

    public void deleteById(final long reservationId) {
        String deleteById = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(deleteById, reservationId);
    }
}
