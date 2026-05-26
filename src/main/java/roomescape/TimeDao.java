package roomescape;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(Time time) {
        String insertSql = """
                INSERT INTO time (time) 
                VALUES (?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setString(1, time.getTime().toString()); // LocalTime을 "10:00" 형태의 String으로 DB에 저장
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<Time> findAll() {
        String selectAllSql = """
                SELECT id, time 
                FROM time
                ORDER BY id ASC
                """;

        return jdbcTemplate.query(selectAllSql, (rs, rowNum) -> new Time(
                rs.getLong("id"),
                LocalTime.parse(rs.getString("time"))
        ));
    }

    public int deleteById(Long id) {
        String deleteSql = """
                DELETE FROM time 
                WHERE id = ?
                """;

        return jdbcTemplate.update(deleteSql, id);
    }
}
