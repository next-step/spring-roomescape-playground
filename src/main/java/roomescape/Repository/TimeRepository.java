package roomescape.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.Domain.Time;

import javax.sql.DataSource;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Time> timeRowMapper = (ResultSet, rowNum) -> new Time
            (
                    ResultSet.getLong("id"),
                    ResultSet.getString("time")
            );

    public Time findTimeReservationById(Long id) {
        String sql = "select * from time where id = ?";
        return jdbcTemplate.queryForObject(sql, timeRowMapper, id);
    }

    public List<Time> findAllTimeReservations()
    {
        String sql = "select * from time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Long createTimeReservation(String time) {
        if (time == null) {
            throw new IllegalArgumentException("누락된 사항이 있습니다. 확인해주세요.");
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("time", time);

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public void deleteTimeReservation(Long id) {
        String sql = "delete from time where id = ? ";
        int rowsCheck = jdbcTemplate.update(sql, id);
        if(rowsCheck == 0)
        {
            throw new NoSuchElementException("지우려는 아이디가 존재하지 않습니다." + id);
        }
    }
}
