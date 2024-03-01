package roomescape.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Domain.Time;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class TimeDAO {
    private final JdbcTemplate jdbcTemplate;

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Time findTimeReservation(Long id) {
        String sql = "select * from time where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, rowNum) ->
                new Time(
                        resultSet.getLong("id"),
                        resultSet.getString("time")
                )
        );
    }

    public List<Time> findAllTimeReservation()
    {
        String sql = "select id, time from time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) ->
        {
            Time time = new Time(
                    resultSet.getLong("id"),
                    resultSet.getString("time")
            );
            return time;
        });
    }

    public Long createTimeReservation(Time time) {
        String sql = "insert into reservation (time) values (?)";

        if (time == null || !time.notEmpty()) {
            throw new IllegalArgumentException("누락된 사항이 있습니다. 확인해주세요.");
        }

        return insertWithKeyHolder(time);
    }

    public void deleteTimeReservation(Long id) {
        String sql = "delete from time where id = ? ";
        int rowsCheck = jdbcTemplate.update(sql, id);
        if(rowsCheck == 0)
        {
            throw new NoSuchElementException("지우려는 아이디가 존재하지 않습니다." + id);
        }
    }

    private Long insertWithKeyHolder(Time time) {
        String sql = "insert into time (time) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.getTime().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

}
