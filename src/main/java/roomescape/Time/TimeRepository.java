package roomescape.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class TimeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Time insertTime(Time time) {
        Time newTime = time.toEntity(time);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into time (time) values (?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps=  connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, newTime.getTime());
            return  ps;
        },keyHolder);

        Long id = keyHolder.getKey().longValue();
        newTime.setId(id);
        return newTime;
    }

    public List<Time> SelectAll() {
        String sql = "select id, time from time";
        List<Time> newTime = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Time time = new Time(
                    rs.getLong("id"),
                    rs.getString("time"));
            return time;
        });
        return newTime;
    }

    public void DeleteById(Long id) {
        String sql  = "delete from time where id = ?";
        int num = jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
