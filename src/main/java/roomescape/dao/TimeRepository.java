package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.util.List;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAll() {
        return jdbcTemplate.query(
                "select * from time",
                (resultSet, rowNum) ->
                        new Time(
                                resultSet.getInt("id"),
                                resultSet.getTime("time").toLocalTime()
                        )
        );
    }

    // SqlParameterSource의 구현체 변경 (-> BeanPropertySqlParameterSource)
// 더 간단하고, 정의한 Entity를 기준으로 바인딩
    public Time save(Time time) {
        String sql = "INSERT INTO time (time) VALUES (:time)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(time);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);
        long generatedKey = keyHolder.getKey().longValue();
        return new Time((int) generatedKey, time.getTime());
    }

    public int delete(int id) {
        String sql = "delete from time where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
