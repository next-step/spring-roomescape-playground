package roomescape.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import javax.sql.DataSource;

import java.util.List;

import static roomescape.query.TimeQuery.FIND_ALL;
import static roomescape.query.TimeQuery.FIND_BY_ID;


@Repository
public class TimeRepository {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    public TimeRepository(JdbcTemplate template, DataSource source) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(source)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        return template.query(FIND_ALL.getQuery(),
                (rs, rowNum) -> new Time(rs.getLong("id"), rs.getTime("time").toLocalTime()));
    }

    public roomescape.domain.Time findById(Long id) {
        return template.queryForObject(FIND_BY_ID.getQuery(),
                ((rs, rowNum) -> new Time(rs.getLong("id"),rs.getTime("time").toLocalTime())), id);
    }


    public Long save(Time newTime) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(newTime);

        return insert.executeAndReturnKey(params).longValue();
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM TIME WHERE id = ?";
        template.update(sql, id);
    }

}
