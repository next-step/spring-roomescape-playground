package roomescape.entity.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Dto.TimeInDto;
import roomescape.entity.Dto.TimeOutDto;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(source)
            .withTableName("time")
            .usingGeneratedKeyColumns("id");
    }

    public List<TimeOutDto> findAll() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new TimeOutDto(
                rs.getLong("id"),
                rs.getString("time")));
    }

    public TimeOutDto save(TimeInDto timeInDto) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("time", timeInDto.getTime());
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new TimeOutDto(id, timeInDto.getTime());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
