package roomescape.repository;


import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeDao {
    private final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
