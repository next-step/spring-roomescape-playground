package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeDao(JdbcTemplate jdbcTemplate, DataSource source){
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Time> rowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("id"),
                resultSet.getString("time")
        );
        return time;
    };

    public List<Time> findAll(){
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Time findById(Long id){
        String sql = "SELECT * FROM time where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public TimeResponseDto insert(TimeRequestDto timeRequest){
        SqlParameterSource params = new BeanPropertySqlParameterSource(timeRequest);
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return new TimeResponseDto(id, timeRequest.time());
    }

    public void delete(Long id){
        jdbcTemplate.update("delete from time where id = ?", id);
    }
}

