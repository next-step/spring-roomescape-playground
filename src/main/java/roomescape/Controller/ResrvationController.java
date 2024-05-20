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

@Controller
public class ResrvationController {

    //private List<Reservation> reservations = new ArrayList<>();
    private ReservationRepository reservationRepository;
    private ReservationService reservationService=new ReservationService();

    public ResrvationController(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations(){

        return ResponseEntity.ok(reservationRepository.getReservations());
    }
/*
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody RequestReservation reservation){

        Reservation newReservation=reservationService.saveReservation(reservation,reservations);
        URI location=URI.create("/reservations/"+newReservation.getId());

        return ResponseEntity.created(location).body(newReservation);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> reservationResult(@PathVariable Long id){

        Reservation reservation=reservationService.viewReservation(reservations,id);

        if(reservation!=null)
                return ResponseEntity.ok(reservation);

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {

        reservationService.deleteReservation(reservations,id);

        return ResponseEntity.noContent().build();
    }
*/
}