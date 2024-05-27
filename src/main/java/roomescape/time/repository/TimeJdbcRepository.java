package roomescape.time.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.Time;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@Primary
@RequiredArgsConstructor
public class TimeJdbcRepository implements TimeRepositoryImpl{

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> timeRowMapper = (rs, rowNum) -> {
        return new Time(
                rs.getLong("id"),
                rs.getString("time")
        );
    };

    @Override
    public List<Time> findAll() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }
    @Override
    public Time findById(Long id) {
        String sql = "SELECT * FROM time WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, timeRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Time save(Time time) {

        String sql = "INSERT INTO time (time) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return new Time(generatedId, time.getTime());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
