package roomescape.data.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.data.dao.daoInterface.ReservationTimeDao;
import roomescape.data.entity.ReservationTime;

@Repository
public class ReservationTimeDaoImpl implements ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "insert into time (time) values (?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, java.sql.Time.valueOf(reservationTime.getTime()));
            return ps;
        }, keyHolder);
        return ReservationTime.builder()
                .id(keyHolder.getKey().longValue())
                .time(reservationTime.getTime())
                .build();
    }

    @Override
    public List<ReservationTime> findAll() {
        final String sql = "select id, time from time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"), resultSet.getTime("time").toLocalTime()));
    }

    @Override
    public ReservationTime findById(long id) {
        final String sql = "select id, time from time where id = ?";
        return jdbcTemplate.queryForObject(sql, ReservationTime.class, id);
    }

    @Override
    public void deleteById(long id) {
        final String sql = "delete from time where id = ?";
        int deleted = jdbcTemplate.update(sql, id);
    }
}
