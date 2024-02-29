package roomescape.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.web.dao.rowmapper.ReservationRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcReservationDao implements ReservationDao{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> getAllReservations() {
        String sql = "SELECT " +
                "r.id as reservation_id, " +
                "r.name, " +
                "r.date, " +
                "t.id as time_id, " +
                "t.time as time_value " +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, new ReservationRowMapper());
    }

    @Override
    public Reservation createReservation(Long id, String name, String date, Time time) {
        String sql = "INSERT INTO reservation (id, name, date, time_id) VALUES (?, ?, ?, ?)";

        Long timeId = time.getId();
        jdbcTemplate.update(sql, id,  name, date, timeId);

        String selectSql = "SELECT" +
                "r.id as reservation_id, " +
                "r.name, " +
                "r.date, " +
                "t.id as time_id, " +
                "t.time as time_value " +
                "FROM reservation as r inner join time as t on r.time_id = t.id " +
                "WHERE r.name = ? AND r.date = ? AND r.time_id = ?";
        return jdbcTemplate.queryForObject(selectSql, new Object[]{name, date, timeId}, new ReservationRowMapper());
    }

    @Override
    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) {
        String sql = "SELECT" +
                "r.id as reservation_id," +
                "r.name," +
                "r.date," +
                "t.id as time_id," +
                "t.time as time_value" +
                "FROM reservation as r inner join time as t on r.time_id = t.id " +
                "WHERE r.id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{id}, new ReservationRowMapper()));
    }

}
