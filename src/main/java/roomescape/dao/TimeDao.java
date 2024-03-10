package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TimeRowMapper implements RowMapper<Time> {
    @Override
    public Time mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Time(
                resultSet.getLong("id"),
                resultSet.getString("time")
        );
    }

}

@Repository
public class TimeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final TimeRowMapper rowMapper;

    public TimeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
        this.rowMapper = new TimeRowMapper();
    }

    public List<Time> getAllTimes() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, this.rowMapper);
    }

    public Time insertTime(Time time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time.getTime());

        Number newId = jdbcInsert.executeAndReturnKey(parameters);
        time.setId(newId.longValue());
        return time;
    }

    public int deleteTime(Long id) {
        String sql = "delete from time where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
