package roomescape.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;

@Repository
public class ReservationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public Reservation getReservation(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM reservation WHERE id = " + id,
                new BeanPropertyRowMapper<>(Reservation.class)
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
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
        validateDeleteReservation(id);
        jdbcTemplate.update("DELETE reservation WHERE id = ?", id);
    }

    private void validateDeleteReservation(Long id) {
        if (getReservation(id) == null) {
            throw new IllegalArgumentException("존재하지 않는 id입니다.");
        }
    }
}
