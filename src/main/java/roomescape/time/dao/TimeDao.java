package roomescape.time.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.exception.BadRequestException;
import roomescape.time.domain.Time;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Time(
                        rs.getLong("id"),
                        LocalTime.parse(rs.getString("time"))
                )
        );
    }

    public Time save(Time time) {
        Map<String, Object> params = new HashMap<>();
        params.put("time", time.time().toString());

        Number id = jdbcInsert.executeAndReturnKey(params);
        return new Time(id.longValue(), time.time());
    }

    public void deleteById(Long id) {
        int result = jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
        if (result == 0) {
            throw new BadRequestException("삭제할 시간이 존재하지 않습니다.");
        }
    }

    public Optional<Time> findById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Time(rs.getLong("id"), LocalTime.parse(rs.getString("time"))),
                id
        ).stream().findFirst();
    }
}
