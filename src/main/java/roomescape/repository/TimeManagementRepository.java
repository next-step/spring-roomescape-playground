package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.repository.rowMapper.TimeRowMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TimeManagementRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    public TimeManagementRepository(JdbcTemplate template, DataSource dataSource) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public Time findById(Long newId) {
        Time found = template.queryForObject("select * from time where id = ?"
                , new TimeRowMapper()
                , newId);

        return found;
    }

    public Long save(Time newTime) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(newTime);

        return insert.executeAndReturnKey(params).longValue();
    }

    public List<Time> findAll() {
        List<Time> founds = template.query("select * from time"
                , new TimeRowMapper());

        return founds;
    }

    public void deleteById(Long id) {
        template.update("delete from time where id = ?", id);
    }
}
