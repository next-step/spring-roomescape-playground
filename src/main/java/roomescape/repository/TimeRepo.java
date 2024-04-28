package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.dto.TimeDTO;
import roomescape.entity.Time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Repository
public class TimeRepo {

    private JdbcTemplate jdbcTemplate;

    public TimeRepo(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    public class TimeMapper implements RowMapper<Time> {
        @Override
        public Time mapRow(ResultSet rs, int rowNum) throws SQLException {
            System.out.println("Mapper : " + rs.getLong("id"));
            return new Time(rs.getLong("id"), rs.getString(("time")));
        }
    }



    public Time findById(long id) {
        String sql = "SELECT * FROM TIMES WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new TimeMapper(), id);
    }

    public int insert(TimeDTO time) {
        String sql = "INSERT INTO TIMES (time) values(?)";
        return jdbcTemplate.update(sql, time.getTime());
    }

    public List<Time> findAll() {
        String sql = "SELECT * FROM TIMES";
        return jdbcTemplate.query(sql, new TimeMapper());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM TIMES WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
}
