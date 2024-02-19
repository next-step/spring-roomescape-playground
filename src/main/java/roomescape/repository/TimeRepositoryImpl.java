package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.exception.Time.TimeErrorMessage;
import roomescape.exception.Time.TimeException;

@Repository
public class TimeRepositoryImpl implements TimeRepository {

    private JdbcTemplate jdbcTemplate;

    public TimeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> rowMapper = (rs, rowNum) -> new Time(
            rs.getLong("id"),
            rs.getString("time")
    );

    @Override
    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Time findById(Long id) {
        String sql = "SELECT id, time FROM time where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public Time create(TimeRequest timeRequest) {
        Time time = new Time(timeRequest.time());

        String sql = "INSERT INTO time(time) VALUES(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        return time.toEntity(keyHolder.getKey().longValue());
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int deleteRows = jdbcTemplate.update(sql, id);
        if (deleteRows == 0) {
            throw new TimeException(TimeErrorMessage.NOT_FOUND);
        }
        return deleteRows;
    }
}
