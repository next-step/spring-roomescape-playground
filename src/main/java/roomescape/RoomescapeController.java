package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class RoomescapeController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicInteger index = new AtomicInteger(0);

    @GetMapping("/")
    public String showPage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> showAllReservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation request) {

        checkDuplicateException(request);

        Reservation reservationOfClient = Reservation.createNewReservation(
                index.incrementAndGet(),
                request.getName(),
                request.getDateTime()
        );
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/reservations/" + reservationOfClient.getId());
        reservations.add(reservationOfClient);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(reservationOfClient);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable int id) {
        Reservation reservation = reservations.stream()
                .filter(res -> res.getId() == id)
                .findFirst()
                .orElseThrow(
                        () -> new ReservationException.NotFoundReservationException("ID " + id + "번 예약을 찾을 수 없습니다."));

        reservations.remove(reservation);
    }

    private void checkDuplicateException(Reservation request) {
        boolean isDuplicate = reservations.stream()
                .anyMatch(res -> res.isSameTime(request.getDateTime()));

        if (isDuplicate) {
            throw new ReservationException.DuplicateTimeException();
        }
    }
}
