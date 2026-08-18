package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.NotFoundReservationException;

import java.net.URI;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ReservationController {
    private JdbcTemplate jdbcTemplate;
    private static final int NO_ROWS = 0;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> readReservationList() {
        List<ReservationResponse> reservationResponses = jdbcTemplate.query(
                        "select id, name, date, time from reservation",
                        (rs, rowNum) -> new Reservation(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getObject("date", LocalDate.class),
                                rs.getObject("time", LocalTime.class)
                        )
                ).stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> readReservation(@PathVariable Long id) {
        Reservation reservation = findReservationById(id);

        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        Reservation newReservation = request.toEntity();
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, request.name());
            ps.setObject(2, request.date());
            ps.setObject(3, request.time());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        Reservation savedReservation = new Reservation(
                id,
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime()
        );

        return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId()))
                .body(ReservationResponse.from(savedReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        String sql = "delete from reservation where id = ?";
        int deletedCount = jdbcTemplate.update(sql, Long.valueOf(id));

        if (deletedCount == NO_ROWS) {
            throw new NotFoundReservationException("예약을 찾을 수 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }

    private Reservation findReservationById(Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {
                    Reservation reservation = new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getObject("date", LocalDate.class),
                            rs.getObject("time", LocalTime.class)
                    );
                    return reservation;
                }, id);
    }
}
