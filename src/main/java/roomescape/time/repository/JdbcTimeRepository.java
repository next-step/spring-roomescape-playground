package roomescape.time.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.Time;

import java.util.List;

@Repository("JdbcTimeRepository")
public class JdbcTimeRepository implements TimeRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Time> getTimeRowMapper() {
        return (rs, rowNum) -> new Time(
                rs.getLong("id"),
                rs.getTime("time").toLocalTime()
        );
    }
    @Override
    public List<Time> findAll() {
        String sql = "select * from time";
        return jdbcTemplate.query(sql, getTimeRowMapper());
    }

    @Override
    public Time save(Time time) {
        String sql = "insert into time (time) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, java.sql.Time.valueOf(time.getTime()));
            return ps;
        }, keyHolder);

        time.setId(keyHolder.getKey().longValue());
        return time;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from time where id = ?";
        int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new IllegalArgumentException("시간이 존재하지 않습니다.");
        }
    }


}
