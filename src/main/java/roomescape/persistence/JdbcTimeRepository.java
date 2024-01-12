package roomescape.persistence;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.domain.repository.TimeRepository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@Repository
public class JdbcTimeRepository implements TimeRepository {

    private final RowMapper<Time> rowMapper = (rs, rowNum) -> {
        final Long id = rs.getLong("id");
        final String time = rs.getString("time");
        return new Time.TimeBuilder()
                .id(id)
                .time(LocalTime.parse(time))
                .build();
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Time save(Time time) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "insert into time (time) values (?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.getTime().toString());
            return ps;
        }, keyHolder);
        return new Time.TimeBuilder()
                .id(keyHolder.getKey().longValue())
                .time(time.getTime())
                .build();

    }

    @Override
    public List<Time> findAll() {
        final String sql = "select * from time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void delete(Long id) {
        final String sql = "delete from time where id = ?";
        int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new IllegalArgumentException("해당 id를 가진 시간이 존재하지 않습니다.");
        }
    }

    @Override
    public Time findById(Long id) {
        try {
            final String sql = "select * from time where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (final DataAccessException exception) {
            throw new IllegalArgumentException("해당 id를 가진 시간이 존재하지 않습니다.");
        }
    }
}