package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.NotFoundReservationException;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);
    private JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> readReservationList() {
        List<ReservationResponse> respones = jdbcTemplate.query(
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
        return ResponseEntity.ok(respones);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> readReservation(@PathVariable Long id) {
        Reservation reservation = findReservationById(id);

        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        Reservation newReservation = request.toEntity(index.getAndIncrement());
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(ReservationResponse.from(newReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservation = findReservationById(id);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }

    private Reservation findReservationById(Long id) {
        return reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("예약을 찾을 수 없습니다."));
    }
}
