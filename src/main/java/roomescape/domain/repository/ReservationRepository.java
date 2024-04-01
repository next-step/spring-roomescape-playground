package roomescape.domain.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static roomescape.domain.repository.query.ReservationQuery.*;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        return new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime());
    };

    public List<Reservation> findAll() {
        return jdbcTemplate.query(FIND_ALL.getQuery(), reservationRowMapper);
    }

    public Reservation save(final Reservation reservation) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate());
        params.put("time", reservation.getTime());

        final long savedId = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return new Reservation(savedId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public boolean existById(final Long id) {
        final Long count = jdbcTemplate.queryForObject(EXIST_BY_ID.getQuery(), Long.class, id);
        return count != null && count > 0;
    }

    public int delete(Long id) {
        return jdbcTemplate.update(DELETE.getQuery(), id);
    }

}
