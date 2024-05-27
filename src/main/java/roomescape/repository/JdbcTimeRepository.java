package roomescape.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.model.ReservationTime;
import roomescape.model.ReservationTime;

import javax.sql.DataSource;
import java.util.List;

public class JdbcTimeRepository implements TimeRepository {

    private final SimpleJdbcInsert jdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime timeAdd(ReservationTime time) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(time);
        Number key = jdbcInsert.executeAndReturnKey(param);
        time.setId(key.intValue());
        return time;
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select * from time";
        return jdbcTemplate.query(sql, timeRowMapper());
    }

    private RowMapper<ReservationTime> timeRowMapper() {
        return BeanPropertyRowMapper.newInstance(ReservationTime.class);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("delete from time where id = ?", id);
    }
}
