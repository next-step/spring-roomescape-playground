package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import roomescape.domain.Time;

@Component
public class TimeQueryingDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TimeQueryingDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        final Long id = resultSet.getLong("id");
        final Time time = new Time(resultSet.getString("time"));
        time.setId(id);
        return time;
    };


    public List<Time> getAllTimes(){
        String sql = "select * from time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }
}
