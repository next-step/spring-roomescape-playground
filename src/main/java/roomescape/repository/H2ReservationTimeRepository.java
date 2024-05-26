package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.exception.ErrorMessage;
import roomescape.exception.ReservationException;

@Repository
public class H2ReservationTimeRepository implements ReservationTimeRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ReservationTimeRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("times")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime save(final ReservationTimeRequest request) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(request);
        final long savedId = jdbcInsert.executeAndReturnKey(param).longValue();
        return new ReservationTime(savedId, request.time());
    }

    @Override
    public ReservationTime findById(final Long id) {
        String sql = "select * from times where id = :id";
        final Map<String, Long> param = Map.of("id", id);
        return namedParameterJdbcTemplate.queryForObject(
                sql, param,
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getObject("time", LocalTime.class)
                )
        );
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select * from times";
        return namedParameterJdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getObject("time", LocalTime.class)
                )
        );
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "delete from times where id = :id";
        Map<String, Long> param = Map.of("id", id);
        final int rowNum = namedParameterJdbcTemplate.update(sql, param);
        if (rowNum == 0) {
            throw new ReservationException(ErrorMessage.INVALID_ID_REQUEST);
        }
    }

}
