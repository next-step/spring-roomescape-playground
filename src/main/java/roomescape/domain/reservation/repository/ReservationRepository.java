package roomescape.domain.reservation.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.domain.reservation.domain.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
        resultSet.getLong("id"),
        resultSet.getString("name"),
        LocalDate.parse(resultSet.getString("date")),
        LocalTime.parse(resultSet.getString("time"))
    );

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long addReservation(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into reservation(name, date, time) values(?, ?, ?)", new String[] {"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean deleteReservation(Long id) {
        int affectRowCount = jdbcTemplate.update("delete from reservation where id = ?", id);
        return affectRowCount > 0;
    }

    public Optional<Reservation> getReservationById(Long id) {
        Reservation reservation = jdbcTemplate.queryForObject("select * from reservation where id = ?", rowMapper, id);
        return Optional.of(reservation);
    }

    public List<Reservation> getAllReservation() {
        return jdbcTemplate.query("select * from reservation", rowMapper);
    }
}
