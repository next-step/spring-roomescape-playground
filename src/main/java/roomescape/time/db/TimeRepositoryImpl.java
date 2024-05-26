package roomescape.time.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.time.model.TimeRequest;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimeRepositoryImpl implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public final RowMapper<TimeEntity> actorRowMapper = (resultSet, rowNum) -> {
        TimeEntity timeEntity = new TimeEntity(
                resultSet.getLong("id"),
                resultSet.getString("time")
        );
        return timeEntity;
    };

    @Override
    public List<TimeEntity> findAllList() {

        String sql = "select * from time ";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    @Override
    public int countTime() {

        String sql = "select count(*) from time";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public TimeEntity findTimeById(Long id) {

        String sql = "select * from time where id= ?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, id);
    }

    @Override
    public TimeEntity insertTime(TimeRequest timeRequest) {

        String sql = "insert into time (time) values   (?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setObject(1, timeRequest.getTime());
            return ps;
        }, keyHolder);

        Long getId = keyHolder.getKey().longValue();

        return findTimeById(getId);
    }

    @Override
    public void deleteTime(Long id) {
        String sql = "delete from time where id =?";

        jdbcTemplate.update(sql, id);
    }
}
