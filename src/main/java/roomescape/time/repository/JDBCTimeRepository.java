package roomescape.time.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.model.Time;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCTimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Long id = resultSet.getLong("id");
        LocalTime time_value = LocalTime.parse(resultSet.getString("time"));

        Time time = new Time(time_value);
        time.setId(id);

        return time;
    };

    @Autowired
    public JDBCTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Time> findTimeById(Long id) throws EmptyResultDataAccessException {
        String sql = "select * from time where id = ?";

        return java.util.Optional.ofNullable(jdbcTemplate.queryForObject(sql, timeRowMapper, id));
    }

    @Override
    public Optional<Time> findTimeByValue(String value) throws EmptyResultDataAccessException {
        String sql = "select * from time where time = ?";

        return java.util.Optional.ofNullable(jdbcTemplate.queryForObject(sql, timeRowMapper, value));
    }

    @Override
    public List<Time> findAllTime() {
        String sql = "select * from time";
        List<Time> timeList = jdbcTemplate.query(sql, timeRowMapper);

        return timeList;
    }

    @Override
    public Long insertWithKeyHolder(Time time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into time (time) values (?)",
                    new String[]{"id"}
            );
            ps.setString(1, time.getTime().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void delete(Time time) {
        String sql = "delete from time where id = ?";
        jdbcTemplate.update(sql, time.getId());
    }
}