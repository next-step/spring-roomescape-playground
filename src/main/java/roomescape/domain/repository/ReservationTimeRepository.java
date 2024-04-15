package roomescape.domain.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static roomescape.domain.repository.query.ReservationTimeQuery.*;

@Repository
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (rs, rowNum) -> {
        return new ReservationTime(
                rs.getLong("id"),
                rs.getTime("time").toLocalTime());
    };
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(FIND_ALL.getQuery(), reservationTimeRowMapper);

    }

    public Optional<ReservationTime> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID.getQuery(), reservationTimeRowMapper, id)
                .stream()
                .findAny();
    }

    public ReservationTime save(final ReservationTime reservationTime) {
        final Map<String, Object> params = new HashMap<>();
        params.put("time", reservationTime.getTime());

        final long savedId = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return new ReservationTime(savedId, reservationTime.getTime());
    }

    public boolean existById(final Long id) {
        final Long count = jdbcTemplate.queryForObject(EXIST_BY_ID.getQuery(), Long.class, id);
        return count != null && count > 0;
    }

    public int delete(final Long id) {
      return jdbcTemplate.update(DELETE.getQuery(), id);
    }
}
