package roomescape.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationRepository;
import roomescape.dto.ReservationDto;
import roomescape.domain.Reservation;
import roomescape.exception.NotFindReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicInteger index = new AtomicInteger(1);
    private ReservationRepository reservationRepository;
    public ReservationController(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }
    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }
    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationRepository.getAllReservations();
    }
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@Validated @RequestBody ReservationDto reservationdto) {
        Reservation reservation = new Reservation(index.getAndIncrement(),reservationdto.getName(), reservationdto.getDate(),reservationdto.getTime());
        reservationRepository.save(reservationdto);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            Reservation reservation = reservationRepository.getReservationById(id);
            if (reservation == null) {
                return ResponseEntity.notFound().build();
            }
            reservationRepository.deleteReservationById(id);
            return ResponseEntity.noContent().build();
        } catch(NotFindReservationException e){
            return ResponseEntity.notFound().build();
        }
    }
}
