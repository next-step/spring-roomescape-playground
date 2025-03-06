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
import roomescape.exception.DataInvalidException;

@Repository
public class TimeJdbcDAO implements TimeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Time> rowMapper;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeJdbcDAO(JdbcTemplate jdbcTemplate, TimeRowMapper timeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = timeRowMapper;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Time create(Time time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time.getTime());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new Time(generatedId.longValue(), time.getTime());
    }

    @Override
    public List<Time> getAll() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM time WHERE id = ?";

        int rowAffected = jdbcTemplate.update(sql, id);
        if (rowAffected == 0) {
            throw new DataInvalidException("시간표를 찾을 수 없습니다. ID : " + id);
        }
    }

    @Override
    public Time getById(long id) {
        String sql = "SELECT * FROM time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }


}
