package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import roomescape.exception.InvalidReservationParameterException;
import roomescape.exception.NotFoundReservationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, new ReservationRowMapper());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Map<String, String> params) {
        if (params.get("name") == null || params.get("name").isEmpty() ||
                params.get("date") == null || params.get("date").isEmpty() ||
                params.get("time") == null || params.get("time").isEmpty()) {
            throw new InvalidReservationParameterException("예약 내용에 누락된 부분이 있습니다.");
        }

        Reservation reservation = new Reservation(params.get("name"), params.get("date"), params.get("time"));

        String sql = "INSERT INTO reservation (name,date,time) VALUES (?,?,?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/")
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int rowAffected = jdbcTemplate.update(sql, id);

        if (rowAffected == 0) {
            throw new NotFoundReservationException("삭제하려는 예약이 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }

    private static class ReservationRowMapper implements RowMapper<Reservation> {
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            Reservation reservation = new Reservation(rs.getString("name"), rs.getString("date"), rs.getString("time"));
            reservation.setId(rs.getLong("id"));
            return reservation;
        }
    }
}
