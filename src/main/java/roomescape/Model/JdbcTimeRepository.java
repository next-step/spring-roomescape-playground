package roomescape.Model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JdbcTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public Time save(Time time) {
        String sql = "INSERT INTO time (time) values (:time)";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("time", time.getTime());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        long generatedKey = keyHolder.getKey().longValue();
        return new Time(generatedKey, time.getTime());
    }

    public List<Time> findAll() {
        String sql = "SELECT * FROM time";
        List<Time> times = jdbcTemplate.query(sql,
                (rs, rowNum) -> new Time(
                        rs.getLong("id"),
                        rs.getString("time")
                ));
        return times;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int updateRow = jdbcTemplate.update(sql, id);
        if (updateRow == 0) {
            throw  new IllegalArgumentException("삭제할 데이터가 없습니다");
        }
    }

    public Time findById(Long id) {
        String sql = "SELECT * FROM time WHERE id = ?";
        Time time = jdbcTemplate.queryForObject(sql,
                ((rs, rowNum) -> new Time(
                        rs.getLong("id"),
                        rs.getString("time")
                )), id);
        return time;
    }
}
