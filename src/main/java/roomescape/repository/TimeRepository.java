package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

//예약 가능한 시간 관리
@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> rowMapper = (rs, rowNum) -> new Time(
            rs.getLong("id"),
            LocalTime.parse(rs.getString("time"))
    );

    public List<Time> findAll() {//time 테이블 전체 조회
        return jdbcTemplate.query("SELECT id, time FROM time", rowMapper);
    }

    public Long save(LocalTime time) {//새로운 예약 시간 저장
        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.toString());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int countById(Long id) {
        return jdbcTemplate.queryForObject("SELECT COUNT(1) FROM time WHERE id = ?", Integer.class, id);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }

    public Time findById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Time(
                rs.getLong("id"),
                java.time.LocalTime.parse(rs.getString("time"))
        ), id);
    }
}
