package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1 );

    @GetMapping("/reservation")
    public String GetReservation (Model model){
//        reservations = new ArrayList<>();
//        reservations.add(new Reservation(index.incrementAndGet(), "브라운1", "2023-01-01", "10:00"));
//        reservations.add( new Reservation(index.incrementAndGet(),"브라운2", "2023-01-01", "11:00"));
//        reservations.add(new Reservation(index.incrementAndGet(),"브라운3", "2023-01-01", "12:00"));
        model.addAttribute("reservations",reservations);
        return "reservation";
    }
    @PostMapping("/reservation")
    public String PostReservation (@RequestBody Reservation reservation,Model model ){
        Reservation newReservation = Reservation.toEntity( reservation,index.getAndIncrement());
        reservations.add(newReservation);
        model.addAttribute("reservations",reservations);
        return "reservation";
    }
//    @DeleteMapping("/reservations")
//    public String DeleteReservation (@RequestBody Reservation reservation,){
//        Reservation newReservation = Reservation.toEntity( reservation,index.incrementAndGet());
//        reservations.add(newReservation);
//        model.addAttribute("reservations",reservations);
//        return "reservation";
//    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> GetReservations (){
        return ResponseEntity.ok().body(reservations);
    }


    @PostMapping("/reservations")
    public ResponseEntity<Reservation> PostReservations ( @RequestBody Reservation reservation ,Model model){
        System.out.println("bb : " + reservations +",  " + index );
        Reservation newReservation = Reservation.toEntity( reservation,index.getAndIncrement());
        System.out.println("aaa : "+ newReservation);
        model.addAttribute("reservations",reservations);
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }


//    @DeleteMapping("/reservations")
//    public ResponseEntity<List<Reservation>> DeleteReservations (){
//        return ResponseEntity.ok().body(reservations);
//    }

}
