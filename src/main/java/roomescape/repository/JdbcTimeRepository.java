package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import roomescape.domain.Time;

@Repository
@RequiredArgsConstructor
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT * FROM time", timeRowMapper());
    }

    private RowMapper<Time> timeRowMapper() {
        return (rs, rowNum) -> new Time(
            rs.getLong("id"),
            rs.getTime("time").toLocalTime()
        );
    }

    public Time save(Time request) {
        String sql = "INSERT INTO time (time) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                sql, new String[] {"id"});
            ps.setObject(1, request.getTime());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return new Time(id, request.getTime());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        if (jdbcTemplate.update(sql, id) == 0)
            throw new IllegalArgumentException("존재하지 않는 시간입니다.");
    }
}
