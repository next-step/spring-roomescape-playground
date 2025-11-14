// ReservationController.java
package roomescape.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

import roomescape.model.Reservation;
import roomescape.exception.BadRequestReservationException;

import javax.sql.DataSource;
import java.util.*;

@RestController // RestController로 변경
@RequestMapping("/reservations")
public class ReservationController {
    private JdbcTemplate jdbcTemplate;

    public ReservationController(DataSource dataSource, JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<Reservation> getReservation() {
        String sql = "select id, name, date, time from reservation";
        List<Reservation> reservations = jdbcTemplate.query(
          sql, (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(
              resultSet.getLong("id"),
              resultSet.getString("name"),
              resultSet.getString("date"),
              resultSet.getString("time")
            );
            return reservation;
          }
        );
        return reservations;
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation newReservation) {
        // 예외 조건 추가
        if (newReservation.getName() == null || newReservation.getName().isBlank() ||
            newReservation.getDate() == null || newReservation.getDate().isBlank() ||
            newReservation.getTime() == null || newReservation.getTime().isBlank()) {
            throw new BadRequestReservationException("Required fields are missing.");
        }

        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";

        int insertCount = jdbcTemplate.update(
          sql,
          newReservation.getName(),
          newReservation.getDate(),
          newReservation.getTime()
        );

        if (insertCount <= 0) {
          throw new RuntimeException("Failed to insert reservation");
        }

        Long generatedId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM reservation", Long.class);

        Reservation reservation = new Reservation(
          generatedId,
          newReservation.getName(),
          newReservation.getDate(),
          newReservation.getTime()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
          .header("Location", "/reservations/" + reservation.getId())
          .body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
      int deletedCount = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);        if (deletedCount > 0) {
          return ResponseEntity.noContent().build();
        } else {
          throw new BadRequestReservationException("Reservation not found.");
        }
    }
}
