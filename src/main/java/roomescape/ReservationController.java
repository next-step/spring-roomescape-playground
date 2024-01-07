package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Semaphore;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final Semaphore idSemaphore = new Semaphore(1, true);

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        //Test 코드의 .body("size()", is(3));를 통과시키기 위해 임의로 Reservation을 3개 생성
        //reservations.add(new Reservation(1, "브라운", "2021-08-05", "15:40"));
        //reservations.add(new Reservation(2, "브라운", "2021-08-05", "15:40"));
        //reservations.add(new Reservation(3, "브라운", "2021-08-05", "15:40"));

        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        if (reservation.getName().isEmpty()){
            return handleNotFoundReservationException(new NotFoundReservationException("Name of Reservation is empty"));
        }
        if (reservation.getDate().isEmpty()){
            return handleNotFoundReservationException(new NotFoundReservationException("Date of Reservation is empty"));
        }
        if (reservation.getTime().isEmpty()){
            return handleNotFoundReservationException(new NotFoundReservationException("Time of Reservation is empty"));
        }
        try {
            idSemaphore.acquire();
            long maxId = 0;
            for (Reservation r : reservations) {
                if (r.getId() > maxId) {
                    maxId = r.getId();
                }
            }
            reservation.setId(maxId + 1);
            reservations.add(reservation);
        } catch (InterruptedException e) {
            return create(reservation);
        } finally {
            idSemaphore.release();
        }

        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).contentType(MediaType.APPLICATION_JSON).body(reservation);
    }
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        /** Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElse(null);
        if(reservation == null) {
            return ResponseEntity.badRequest().build();
        }
        reservations.remove(reservation);
         **/

        // orElse 대신 optional을 사용
        Optional<Reservation> reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst();

        if(reservation.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        reservations.remove(reservation.get());

        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleNotFoundReservationException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }

}
