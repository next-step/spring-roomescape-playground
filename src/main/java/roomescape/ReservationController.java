package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private static final AtomicLong idCounter = new AtomicLong();
    private final List<Reservation> reservations = new ArrayList<>();

    @Autowired
    ReservationQueryingDAO reservationQueryingDAO;

    @Autowired
    ReservationUpdatingDAO reservationUpdatingDAO;

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservation(){
        //step6
        List<Reservation> reservations = reservationQueryingDAO.getAllReservations();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){

        //빈 값 들어왔을 때의 예외 처리
        if(reservation.getName() == null || reservation.getName().isEmpty() || reservation.getDate() == null || reservation.getTime() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 'time' 필드가 'Time' 객체가 아닐 때의 예외 처리
        if(reservation.getTime().isInvalid()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


        Reservation newReservation = new Reservation();

        newReservation.setName(reservation.getName());
        newReservation.setDate(reservation.getDate());
        newReservation.setTime(new Time(reservation.getTime().getTime()));

        //step4~6
        Number newId = reservationUpdatingDAO.save(newReservation);
        newReservation.setId(newId.longValue());

        return ResponseEntity
                .status(201)
                .location(java.net.URI.create("/reservations/"+newReservation.getId()))
                .body(newReservation);
    }
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) throws Exception {

        int row = reservationUpdatingDAO.delete(id);

        if(row>0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


}
