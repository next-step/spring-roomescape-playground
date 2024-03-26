package roomescape;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    List<Reservation> reservations = new ArrayList<>();
    ReservationService reservationService;


    //생성자로 ReservationService 에 대한 의존성을 주입해요.
    public ReservationController (ReservationService reservationService){
        this.reservationService=reservationService;
    }

    @GetMapping("/")
    public String GoToHome(){
        // 수정 전
        // return "templates/home";
        // 수정 후
        return "home";
    }

    //reservation 이동
    @GetMapping("/reservation")
    public void reservation(){
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> read(){
        return reservationService.getAllReservations();
    }
    final AtomicLong index = new AtomicLong(0);
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(index,reservation);
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
