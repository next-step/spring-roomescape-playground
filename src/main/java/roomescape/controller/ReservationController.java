package roomescape.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.Reservation;
import roomescape.exception.IllegalReservationException;
import roomescape.exception.NotFoundReservationException;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            if (reservation == null || reservation.getDate() == null || reservation.getTime() == null
                    || reservation.getName().isEmpty()) {
                throw new IllegalReservationException("Reservation의 항목이 채워지지 않았습니다");
            }
            addReservationToDatabase(reservation);

            return ResponseEntity
                    .<Reservation>status(CREATED)
                    .location(java.net.URI.create("/reservations/" + reservation.getId()))
                    .body(reservation);
        } catch (IllegalReservationException e) {
            return ResponseEntity.<Reservation>status(HttpStatus.BAD_REQUEST).body(reservation);
        } catch (Exception e) {
            return ResponseEntity.<Reservation>status(HttpStatus.INTERNAL_SERVER_ERROR).body(reservation);
        }
    }


    @GetMapping
    public List<Reservation> getReservation() {
        return getReservationsFromDatabase();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) {
        try {
            Reservation existingReservation = getReservationFromDatabase(id);
            deleteReservationFromDatabase(id);

            return ResponseEntity.noContent().build();
        } catch (NotFoundReservationException e) {
            throw new NotFoundReservationException("[ERROR] 해당 ID의 Reservation이 없음");
        }
    }

    private Reservation getReservationFromDatabase(long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new ReservationRowMapper(), id);

        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundReservationException("[ERROR] 예약 아이디: " + id + " 없음");
        }
    }

    private void deleteReservationFromDatabase(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    @GetMapping("/from-database")
    public List<Reservation> getReservationsFromDatabase() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, new ReservationRowMapper());
    }

    private static class ReservationRowMapper implements RowMapper<Reservation> {
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            LocalDate date = LocalDate.parse(rs.getString("date"));
            LocalTime time = LocalTime.parse(rs.getString("time"));

            return new Reservation(id, name, date, time);
        }

    }

    private void addReservationToDatabase(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTimeId());
            return ps;
        }, keyHolder);

        long generatedId = (long) keyHolder.getKey();
        reservation.setId(generatedId);

        String selectSql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r " +
                "INNER JOIN time as t ON r.time_id = t.id " +
                "WHERE r.id = ?";

        Reservation updatedReservation = jdbcTemplate.queryForObject(selectSql, new ReservationWithTimeRowMapper(), generatedId);

        if (updatedReservation != null) {
            reservation.setTimeId(updatedReservation.getTimeId());
            reservation.setTime(updatedReservation.getTime());
        }
    }

    private static class ReservationWithTimeRowMapper implements RowMapper<Reservation> {
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            long reservationId = rs.getLong("reservation_id");
            String name = rs.getString("name");
            LocalDate date = LocalDate.parse(rs.getString("date"));
            long timeId = rs.getLong("time_id");
            LocalTime time = LocalTime.parse(rs.getString("time_value"));

            return new Reservation(reservationId, name, date, timeId, time);
        }
    }


}
