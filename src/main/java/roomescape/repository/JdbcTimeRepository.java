package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.TimeDomain;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JdbcTimeRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<TimeDomain> findAll() {
        String sql = "select * from time";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new TimeDomain(
                rs.getLong("id"),
                rs.getString("time")
        ));
    }

    @Override
    public TimeDomain save(TimeDomain time){
        Map<String , String> parameters = new HashMap<>();
        parameters.put("time", time.getTime());
        long id = jdbcInsert.executeAndReturnKey(parameters).longValue();
        return new TimeDomain(id,time.getTime());
    }


    @Override
    public void deleteById(Long id) {
        String sql = "delete from time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public TimeDomain findById(final Long id) {
        final String sql = "SELECT * FROM time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new TimeDomain(
                rs.getLong("id"),
                rs.getString("time")
        ), id);
    }
}

