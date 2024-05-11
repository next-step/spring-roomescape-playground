package roomescape.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeDto;
import roomescape.exception.CustomException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;

@Repository
public class TimeDAO {
    private final JdbcTemplate jdbcTemplate;

    public TimeDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;

    }

    private final RowMapper<Time> rowMapper = (resultSet, rowNum) -> {
        return Time.builder()
                .id(resultSet.getLong("id"))
                .time(LocalTime.parse(resultSet.getString("time")))
                .build();
    };

    public List<TimeDto> getTimes() {

        try {


             return jdbcTemplate.query("SELECT * FROM time", rowMapper).stream().map(time -> {
                return TimeDto.builder().id(time.getId()).time(time.getTime()).build();
            }).toList();

        } catch (DataAccessException exception) {
            throw new CustomException();
        }
    }

    public Time findTimeById(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM time WHERE ID = ?", rowMapper, id);
        } catch (DataAccessException exception) {
            throw new CustomException();
        }
    }

    public int postTime(TimeDto timeDto) {
        System.out.println(timeDto.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO time(time) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, timeDto.getTime().toString());
                return ps;
            }, keyHolder);
            return keyHolder.getKey().intValue(); // 자동 생성된 키 값 반환
        } catch (DataAccessException exception) {
            throw new CustomException();
        }
    }
    public void deleteTime(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
        } catch (DataAccessException exception) {
            throw new CustomException();
        }
    }


}
