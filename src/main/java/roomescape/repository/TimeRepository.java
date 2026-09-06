package roomescape.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.exception.DuplicateTimeException;
import roomescape.exception.TimeInUseException;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) ->
            new Time(
                    resultSet.getLong("id"),
                    resultSet.getObject("time", LocalTime.class)
            );

    public List<Time> findAll() {
        String sql = "select id, time from time order by id asc";

        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Optional<Time> findById(Long id) {
        String sql = "select id, time from time where id = ?";

        return jdbcTemplate.query(sql, timeRowMapper, id)
                .stream()
                .findFirst();
    }

    public Time save(Time time) {
        String sql = "insert into time (time) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
                preparedStatement.setObject(1, time.getTime());
                return preparedStatement;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            throw new DuplicateTimeException("이미 등록된 시간입니다.");
        }

        Long id = keyHolder.getKey().longValue();
        return new Time(id, time.getTime());
    }

    public int deleteById(Long id) {
        String sql = "delete from time where id = ?";

        try {
            return jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new TimeInUseException(
                    "예약에서 사용 중인 시간은 삭제할 수 없습니다. 관련 예약을 먼저 취소해 주세요."
            );
        }

    }
}
