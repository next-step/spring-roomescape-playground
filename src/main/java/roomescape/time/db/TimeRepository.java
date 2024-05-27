package roomescape.time.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;

@Primary
@Repository
@RequiredArgsConstructor
public class TimeRepository implements TimeRepositoryImpl {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<TimeEntity> rowMapper = (rs, rowNum) -> {
        return TimeEntity.builder()
                .id(rs.getLong("id"))
                .time(rs.getString("time"))
                .build();
    };


    @Override
    public List<TimeEntity> findAll() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, rowMapper);

    }

    @Override
    public TimeEntity findById(Long id) {
        String sql = "SELECT *FROM time WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public TimeEntity save(TimeEntity timeEntity) {

        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, timeEntity.getTime());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return new TimeEntity(generatedId, timeEntity.getTime());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
