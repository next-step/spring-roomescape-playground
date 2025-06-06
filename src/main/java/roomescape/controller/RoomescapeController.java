package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.InvalidReservationException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index;

    public RoomescapeController() {
        this.index = new AtomicLong(1);
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationPage(Model model) {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> reservations() {
        List<ReservationResponse> result = new ArrayList<>();
        for (Reservation r : reservations) {
            result.add(new ReservationResponse(r.getId(), r.getName(), r.getDate(), r.getTime()));
        }
        return result;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty() ||
                request.getDate() == null || request.getDate().trim().isEmpty() ||
                request.getTime() == null || request.getTime().trim().isEmpty()) {
            throw new InvalidReservationException("모든 필드는 필수로 작성해야합니다.");
        }

        Long newId = index.getAndIncrement();
        Reservation reservation = new Reservation(
                newId,
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        reservations.add(reservation);

        URI location = URI.create("/reservations/" + newId);
        ReservationResponse response = new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );

        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Optional<Reservation> reservation = reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();

        if (reservation.isPresent()) {
            reservations.remove(reservation.get());
            return ResponseEntity.noContent().build();
        } else {
            throw new InvalidReservationException("해당 ID의 예약이 존재하지 않습니다.");
        }
    }
}
