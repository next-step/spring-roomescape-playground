package roomescape.repository.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.TimeDTO;
import roomescape.repository.domain.Time;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimeDao {


    static class TimeRowMapper implements RowMapper<Time> {
        @Override
        public Time mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Time(
                    resultSet.getLong("id"),
                    resultSet.getString("time")
            );
        }
    }

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

    public static TimeDTO mapToTimeDTO(Time time) {
        TimeDTO timeDTO = new TimeDTO();
        timeDTO.setId(time.getId());

        return timeDTO;
    }

    public TimeDTO getTimeById(Long id) {
        String sql = "SELECT * FROM time WHERE id = ?";
        TimeDTO timeDTO = mapToTimeDTO(jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper));
        return timeDTO;
    }

    public List<Time> getAllTimes() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, this.rowMapper);
    }

    public Time insertTime(Time time) {
        if (time.getTime() == null || time.getTime().isEmpty()) {
            throw new IllegalArgumentException("Time field cannot be null or empty");
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time.getTime());

        Number newId = jdbcInsert.executeAndReturnKey(parameters);
        long generatedId = newId != null ? newId.longValue() : 0L;  // null 체크

        // 생성된 ID를 Time 객체에 할당
        time.setId(generatedId);
        return time;
    }

    public void deleteTimeById(Long id) {
        String sql = "delete from time where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
