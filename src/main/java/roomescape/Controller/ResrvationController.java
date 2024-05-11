package roomescape.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Model.RequestReservation;
import roomescape.Model.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Controller
public class ResrvationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong atomicLong = new AtomicLong(0);

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations(){

        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody RequestReservation reservation){

        Reservation newReservation=new Reservation(atomicLong.incrementAndGet(), reservation.getName(), reservation.getDate(), reservation.getTime());
        URI location=URI.create("/reservations/"+newReservation.getId());

        return ResponseEntity.created(location).body(newReservation);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> reservationResult(@PathVariable("id") Long id){
        for(Reservation reservation:reservations)
            if(reservation.getId()==id)
                return ResponseEntity.ok(reservation);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id){
        for(Reservation reservation:reservations)
            if(reservation.getId()==id)
                reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }

}
