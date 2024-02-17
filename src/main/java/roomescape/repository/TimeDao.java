package roomescape.repository;


import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TimeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Time> timeRowMapper() {
        return (rs, rowNum) -> {
            Time time = new Time(
                    rs.getLong("id"),
                    rs.getTime("time").toLocalTime()
            );
            return time;
        };
    }

    public List<Time> findAllTime() {
        return jdbcTemplate.query("select * from time", timeRowMapper());
    }

    public Time findTimeById(Long id) {
        String sql = "select id, time from time where id = ?";
        return jdbcTemplate.queryForObject(sql, timeRowMapper(), id);
    }

    public Long saveTime(Time time) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("time").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = Map.of(
                "time", time.getTime()
        );
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return key.longValue();
    }

    public void deleteTimeById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
