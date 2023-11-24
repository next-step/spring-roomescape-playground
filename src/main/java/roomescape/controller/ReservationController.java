package roomescape.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    private List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(0);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            if (reservation == null || reservation.getDate() == null || reservation.getTime() == null
                    || reservation.getName().isEmpty()) {
                throw new IllegalReservationException("Reservation의 항목이 채워지지 않았습니다");
            }
            System.out.println("성공티비");
            addReservationToDatabase(reservation); // database에 추가

            reservations.add(reservation); // 리스트에 예약정보 추가


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
        return getReservationsFromDatabase(); // Return reservations from memory instead of the database
//        return reservations;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) {
        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            Reservation reservation = iterator.next();
            if (reservation.getId() == id) {
                iterator.remove();
                counter.decrementAndGet();
                deleteReservationFromDatabase(reservation.getId());
                return ResponseEntity.noContent().build();
            }
        }

        throw new NotFoundReservationException("Reservation with ID " + id + " not found");
    }

    private void deleteReservationFromDatabase(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Reservation getReservationFromDatabase(long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new ReservationRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            // If no reservation is found, throw a custom exception
            throw new NotFoundReservationException("Reservation with ID " + id + " not found");
        }
    }

    @GetMapping("/from-database")
    public List<Reservation> getReservationsFromDatabase() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, new ReservationRowMapper());
    }

    private static class ReservationRowMapper implements RowMapper<Reservation> {
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            Reservation reservation = new Reservation();
            reservation.setId(rs.getLong("id"));
            reservation.setName(rs.getString("name"));
            reservation.setDate(LocalDate.parse(rs.getString("date")));
            reservation.setTime(LocalTime.parse(rs.getString("time")));
            return reservation;
        }
    }


    private void addReservationToDatabase(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());

        Reservation latestReservation = getLatestReservationFromDatabase();

        reservations.add(latestReservation);
    }

    private Reservation getLatestReservationFromDatabase() {
        String sql = "SELECT * FROM reservation ORDER BY id DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, new ReservationRowMapper());
    }

}
