package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.InvalidReservationException;
import roomescape.model.Reservation;
import roomescape.model.Time;
import roomescape.repository.ReservationRepository;

import java.net.URI;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/reservations")
public class ReservationAPIController {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationAPIController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    @GetMapping
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Map<String, String> params) {
        String name=params.get("name");
        String date=params.get("date");
        String timeIdStr=params.get("time_id");

        if (timeIdStr==null) {
            throw new InvalidReservationException("timeid 값 오류");
        }

        if (name == null || name.isBlank() || date == null || date.isBlank()) {
            throw new InvalidReservationException("name, date, time 모두 필요합니다.");
        }

        Long timeId = Long.valueOf(timeIdStr);
        Time time = new Time();
        time.setId(timeId);

        Reservation reservation = new Reservation(null, name, date, time);
        Reservation saved= reservationRepository.save(reservation);

        return ResponseEntity
            .created( URI.create("/reservations/" + reservation.getId()))
            .body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean deleted = reservationRepository.deleteById(id);

        if (!deleted) {
            throw new InvalidReservationException("삭제 중 오류 발생");
        }

        return ResponseEntity.noContent().build();
    }
}
