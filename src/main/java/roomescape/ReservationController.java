package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1 );

    @GetMapping("/reservation")
    public String GetReservation (Model model){
        model.addAttribute("reservations",reservations);
        return "reservation";
    }
    @PostMapping("/reservation")
    public String PostReservation (@RequestBody Reservation reservation,Model model ){
        Reservation newReservation = Reservation.toEntity(reservation,index.getAndIncrement());
        reservations.add(newReservation);
        model.addAttribute("reservations",reservations);
        return "reservation";
    }
    @DeleteMapping("/reservation")
    public String DeleteReservation (@PathVariable Long id, Model model){
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(reservation);
        model.addAttribute("reservations",reservations);
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> GetReservations (){
        return ResponseEntity.ok().body(reservations);
    }


    @PostMapping("/reservations")
    public ResponseEntity<Reservation> PostReservations ( @RequestBody Reservation reservation ){
        Reservation newReservation = Reservation.toEntity( reservation,index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> DeleteReservations (@PathVariable Long id){
        System.out.println("Aa : " + id);
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }

}
