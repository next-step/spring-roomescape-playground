package roomescape.domain.reservation.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.domain.reservation.domain.Reservation;

@Repository
public class ReservationRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    KeyHolder keyHolder = new GeneratedKeyHolder();

    private final RowMapper<Reservation> rowMapper = ((resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date")),
            LocalTime.parse(resultSet.getString("time"))
        );
        return reservation;
    });

    public Reservation addReservation(Reservation reservation) {
        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement("insert into reservation(name, date, time) values(?, ?, ?)", new String[] {"id"});
                ps.setString(1, reservation.getName());
                ps.setString(2, reservation.getDate().toString());
                ps.setString(3, reservation.getTime().toString());
                return ps;
            }, keyHolder);
        long generatedId = keyHolder.getKey().longValue();
        return getReservationById(generatedId).get();
    }

    public boolean deleteReservation(Long id) {
        int ret = jdbcTemplate.update("delete from reservation where id = ?", id);
        return ret > 0;
    }

    public Optional<Reservation> getReservationById(Long id) {
        Reservation reservation = jdbcTemplate.queryForObject("select * from reservation where id = ?", rowMapper, id);
        return Optional.of(reservation);
    }

    public List<Reservation> getAllReservation() {
        List<Reservation> reservations = jdbcTemplate.query("select * from reservation", rowMapper);
        return reservations;
    }

}
