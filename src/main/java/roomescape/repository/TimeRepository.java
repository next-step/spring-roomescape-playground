package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("TIME")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> getTimes() {
        String sql = "SELECT id, time FROM TIME";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> mapTime(resultSet)
        );
    }

    public Time saveTime(LocalTime time) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("time", time.toString());

        Number id = simpleJdbcInsert.executeAndReturnKey(params);

        return new Time(
                id.longValue(),
                time
        );
    }

    public Optional<Time> getTime(long id) {
        String sql = "SELECT id, time FROM TIME WHERE id = ?";

        List<Time> times = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> mapTime(resultSet),
                id
        );

        return times.stream().findFirst();
    }

    public int deleteTime(long id) {
        String sql = "DELETE FROM TIME WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }

    private Time mapTime(ResultSet resultSet) throws SQLException {
        return new Time(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("time"))
        );
    }
}
