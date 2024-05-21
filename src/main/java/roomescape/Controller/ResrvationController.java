package roomescape.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Model.RequestReservation;
import roomescape.Model.Reservation;
import roomescape.Model.ReservationService;
import roomescape.Repository.ReservationRepository;

import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ResrvationController {

    private AtomicLong atomicLong = new AtomicLong(0);
    private ReservationRepository reservationRepository;

    public ResrvationController(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations(){

        return ResponseEntity.ok(reservationRepository.getAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody RequestReservation reservation){

        String name=reservation.getName();
        String date=reservation.getDate();
        String time=reservation.getTime();

        Reservation newReservation= new Reservation(atomicLong.incrementAndGet(),name,date,time);

        Long id= reservationRepository.createReservation(newReservation);
        URI location=URI.create("/reservations/"+id);

        return ResponseEntity.created(location).body(newReservation);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> reservationResult(@PathVariable Long id){

        Reservation findReservation=reservationRepository.getReservationById(id);

        return ResponseEntity.ok(findReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {

        reservationRepository.deleteReservationById(id);

        return ResponseEntity.noContent().build();
    }

}