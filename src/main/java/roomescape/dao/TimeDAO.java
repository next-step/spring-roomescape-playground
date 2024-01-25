package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeDAO {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("id"),
                resultSet.getTime("time").toLocalTime()
        );
        return time;
    };

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        //예약관리 페이지 구현을 위한 Time 테스트 데이터 생성(8단계 테스트 시 삭제)
//        insertNewTime(new Time(1L, "10:00"));
//        insertNewTime(new Time(2L, "13:00"));
//        insertNewTime(new Time(3L, "17:00"));
    }

    public List<Time> findAllTimes() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Time findSpecificTime(Long time_id) {
        String sql = "SELECT id, time FROM time WHERE id=" + time_id;
        return jdbcTemplate.queryForObject(sql, timeRowMapper);
    }

    public Long insertNewTime(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.getTime().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();   // time_id
    }

    public Long updateTime(Time time, Long id) {
        String sql = "UPDATE time SET time=? WHERE id=?";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, time.getTime().toString());
            ps.setString(2, time.getId().toString());
            return ps;
        });

        return id;
    }

    public void deleteTime(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
