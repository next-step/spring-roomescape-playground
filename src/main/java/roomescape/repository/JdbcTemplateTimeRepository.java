package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import javax.sql.DataSource;
import javax.swing.plaf.basic.BasicTreeUI;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcTemplateTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTimeRepository(DataSource dataSource) {jdbcTemplate = new JdbcTemplate(dataSource); }

    public List<Time> findAllTime() {return jdbcTemplate.query("select * from time", timeRowMapper); }

    public Long insertTime(Time time)  {
        String sql = "insert into time (time) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, time.time());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean deleteTime(Long id) {
        String sql = "delete from time where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id)) == 1;
    }

    private final RowMapper<Time> timeRowMapper = (rs, rowNum) -> {
        Time time = new Time(
                rs.getLong("id"),
                rs.getString("time")
        );
        return time;
    };
}
