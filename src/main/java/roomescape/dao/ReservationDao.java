package roomescape.dao;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;
import roomescape.exception.IdNotExistException;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource()))
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public Reservation getReservationById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM reservation WHERE id = " + id,
                new BeanPropertyRowMapper<>(Reservation.class)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new IdNotExistException();
        }
    }

    public List<Reservation> getAllReservations() {
        return jdbcTemplate.query("SELECT * FROM reservation",
            new BeanPropertyRowMapper<>(Reservation.class)
        );
    }

    public Long saveReservation(Reservation reservation) {
        validateAddReservation(reservation);
        var parameterSource = new BeanPropertySqlParameterSource(reservation);
        Number id = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        reservation.setId(id.longValue());
        return id.longValue();
    }

    private void validateAddReservation(Reservation reservation) {
        if (reservation == null || reservation.getName().isEmpty() || reservation.getDate().isEmpty() || reservation.getTime().isEmpty()) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
    }

    public void removeReservation(Long id) {
        if (!hasReservationById(id)) {
            throw new IdNotExistException();
        }
        jdbcTemplate.update("DELETE reservation WHERE id = ?", id);
    }

    private boolean hasReservationById(Long id) {
        return Boolean.TRUE.equals(
            jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM reservation WHERE id = " + id + ")",
                Boolean.class)
        );
    }
}
