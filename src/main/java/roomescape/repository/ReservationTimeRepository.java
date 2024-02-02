package roomescape.repository;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select id,time from time",
                (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"), resultSet.getString("time"))
        );
    }

    public ReservationTime findById(long id) {
        return jdbcTemplate.queryForObject("select id,time from time where id = ?",
                (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"), resultSet.getString("time")),
                id
        );
    }

    public long save(ReservationTime time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement("insert into time (time) values (?)",
                            new String[]{"id"});
                    ps.setString(1, time.getTime().toString());
                    return ps;
                }, keyHolder
        );

        return requireNonNull(keyHolder.getKey(), "시간을 생성할 수 없습니다. 시간 id가 존재하지 않습니다.").longValue();
    }

    public void deleteById(long id) {
        int updated = jdbcTemplate.update("delete from time where id = ?", id);
        if (updated == 0) {
            throw new NoSuchElementException("예약시간을 삭제할 수 없습니다. 존재하지 않는 예약 시간입니다.");
        }
    }
}
