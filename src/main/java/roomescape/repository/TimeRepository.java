package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.dto.RequestTime;
import roomescape.domain.dto.ResponseTime;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimeRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public TimeRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<ResponseTime> TimeRowMapper = (resultSet, rowNum) -> {
        ResponseTime responseTime = new ResponseTime(
                resultSet.getLong("id"),
                resultSet.getString("time")
        );
        return responseTime;
    };

    public ResponseTime addTime(RequestTime requestTime) {
        Map<String, String> params = new HashMap<>();
        params.put("time", requestTime.time());

        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return new ResponseTime(id, requestTime.time());
    }

    public List<ResponseTime> findAll() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql,
                TimeRowMapper
        );
    }

    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM time WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
}
