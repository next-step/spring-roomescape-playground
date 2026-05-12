package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.exception.BadRequestException;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);
    private final JdbcTemplate jdbcTemplate;

    public RoomescapeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> showReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime()
                ));

        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody @Valid ReservationRequest request) {
        validateReservationDateTime(request.date(), request.time());

        Long id = index.getAndIncrement();

        Reservation reservation = new Reservation(
                id,
                request.name(),
                request.date(),
                request.time()
        );

        validateDuplicate(reservation);
        reservations.add(reservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public  ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));

        if (!removed) {
            throw new BadRequestException("예약번호가 " + id + "인 예약은 존재하지 않습니다.");
        }

        return ResponseEntity.noContent().build();
    }

    private void validateDuplicate(Reservation reservation) {
        boolean duplicated = reservations.stream()
                .anyMatch(savedReservation ->
                        savedReservation.isSameSchedule(reservation));

        if (duplicated) {
            throw new BadRequestException("이미 예약된 날짜와 시간입니다.");
        }
    }

    private void validateReservationDateTime(LocalDate date, LocalTime time) {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time);

        if (reservationDateTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("예약 시간은 현재 시각 이후여야 합니다.");
        }
    }
}
