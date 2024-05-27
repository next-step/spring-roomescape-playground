package roomescape.dao;

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
public class H2ReservationTimeDao implements ReservationTimeDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ReservationTimeDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("times")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime save(final ReservationTime time) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(time);
        final long savedId = jdbcInsert.executeAndReturnKey(param).longValue();
        return new ReservationTime(savedId, time.getTime());
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
