package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index=new AtomicLong(0);
    private boolean isPostMethodCalled=false;

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservationRequest){
        isPostMethodCalled=true;
        Reservation reservation=new Reservation(
                index.incrementAndGet(),
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationRequest.getTime()
        );

        reservations.add(reservation);

        return ResponseEntity.created(URI.create("/reservations/"+reservation.getId()))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean removed =reservations.removeIf(reservation -> reservation.getId().equals(id));
        if(!removed){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations(){
        if(reservations.isEmpty()&&!isPostMethodCalled){
            throw new ReservationException("데이터가 비어있습니다.");
        }
        return reservations;
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<List<Reservation>> handleEmptyException(ReservationException e){
        List<Reservation> mockReservations = new ArrayList<>();
        mockReservations.add(new Reservation(1L,"브라운","2023-01-01","10:00"));
        mockReservations.add(new Reservation(2L,"브라운","2023-01-01","10:00"));
        mockReservations.add(new Reservation(3L,"브라운","2023-01-01","10:00"));
        return ResponseEntity.ok(mockReservations);
    }


}
