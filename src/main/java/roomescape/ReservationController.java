package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    ReservationService reservationService;


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
    public ResponseEntity<Void> create(@RequestBody Reservation reservation) {
        reservationService.addReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + index.incrementAndGet())).build();
    }
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
