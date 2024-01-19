package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private static final AtomicLong idCounter = new AtomicLong();
    private final List<Reservation> reservations = new ArrayList<>();

    @Autowired
    ReservationQueryingDAO reservationQueryingDAO;

    @Autowired
    ReservationUpdatingDAO reservationUpdatingDAO;

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservation(){
        //step 1~3
//        return ResponseEntity.ok(reservations);

        //step6
        List<Reservation> reservations = reservationQueryingDAO.getAllReservations();

        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){

        //빈 값 들어왔을 때의 예외 처리
        if(reservation.getName().isEmpty() || reservation.getDate() == null || reservation.getTime() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Reservation newReservation = new Reservation();
        newReservation.setId(idCounter.incrementAndGet());
        newReservation.setName(reservation.getName());
        newReservation.setDate(reservation.getDate());
        newReservation.setTime(reservation.getTime());

        //step1~3
//        reservations.add(newReservation);


        //step4~6
        reservationUpdatingDAO.save(newReservation);

        return ResponseEntity
                .status(201)
                .location(java.net.URI.create("/reservations/"+newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) throws Exception {
        Reservation deleteOne = null;

        //예약이 없는 경우
        if(reservations.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                deleteOne = reservation;
                break;
            }
        }
        if(deleteOne != null){
            reservations.remove(deleteOne);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


}
