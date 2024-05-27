package roomescape.repository;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Times;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;

import java.time.LocalTime;
import java.util.List;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public Times save(Times times) {
        String sql = "INSERT INTO time(time) VALUES (:time);";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(times);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);
        long generatedKey = keyHolder.getKey().longValue();
        return new Times(generatedKey, times.getTime());
    }

    public List<Times> findAll() {
        String sql = "SELECT * FROM time;";
        return namedParameterJdbcTemplate.query(sql,
                (resultSet, rowNum) -> new Times(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("time"))));
    }

    public void deleteById(int id) {
        String sql = "delete from time where id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);
        int updatedRowCount = namedParameterJdbcTemplate.update(sql, param);
        if (updatedRowCount == 0) {
            throw new NotFoundTimeException("시간이 존재하지 않습니다.");
        }
    }

    public Times findById(Long timeId) {
        String sql = "SELECT * FROM time where id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql,
                    (resultSet, rowNum) -> new Times(
                            resultSet.getLong("id"),
                            LocalTime.parse(resultSet.getString("time"))
                    ), timeId);
        } catch(IncorrectResultSizeDataAccessException e){
            return null;
        }
    }
}
