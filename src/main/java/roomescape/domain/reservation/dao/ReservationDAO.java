package roomescape.domain.reservation.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.time.domain.Time;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
        resultSet.getLong("reservation_id"),
        resultSet.getString("name"),
        LocalDate.parse(resultSet.getString("date")),
        new Time(resultSet.getLong("time_id"), resultSet.getTime("time_value").toLocalTime())
    );

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long addReservation(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into reservation(name, date, time_id) values(?, ?, ?)", new String[] {"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean deleteReservation(Long id) {
        int affectRowCount = jdbcTemplate.update("delete from reservation where id = ?", id);
        return affectRowCount > 0;
    }

    public Optional<Reservation> getReservationById(Long id) {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value FROM reservation as r inner join time as t on r.time_id = t.id where r.id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return Optional.of(reservation);
    }

    public List<Reservation> getAllReservation() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value FROM reservation as r inner join time as t on r.time_id = t.id ";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
