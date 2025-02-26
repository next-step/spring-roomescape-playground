package roomescape.dao.time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Time;

@Repository
public class TimeJdbcDAO implements TimeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Time> rowMapper = new TimeRowMapper();
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeJdbcDAO(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("Time")
                .usingGeneratedKeyColumns("id");
    }

    public Time create(Time time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time.getTime());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new Time(generatedId.longValue(), time.getTime());
    }

    public List<Time> getAll() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, rowMapper);
    }


}
