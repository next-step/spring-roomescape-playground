package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
@RequiredArgsConstructor
public class JdbcTimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> rowMapper = (rs, rowNum) ->
            Time.of(rs.getLong("id"), rs.getString("time"));

    @Override
    public Time save(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return Time.of(generatedId, time.getTime());
    }

    @Override
    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT * FROM time", rowMapper);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }

    @Override
    public Optional<Time> findById(Long id) {
        List<Time> times = jdbcTemplate.query("SELECT * FROM time WHERE id = ?", rowMapper, id);
        return times.stream().findFirst();
    }
}

