package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import roomescape.model.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final List <Reservation> reservations=new ArrayList<>();
    private final AtomicLong index=new AtomicLong(1);

    @GetMapping("/reservation")
    public String reservationPage(){
        return "reservation";

    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations(Model model){
        //mission 1, 2 에 있던 예약 더미데이터들을 넣으면,
        //createReservation 에서 중복 ID 문제가 발생할 수 있기 때문에,
        //코드를 삭제함
        return reservations;

    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(
            @RequestBody Map<String, String> request
    ){
        Reservation reservation=new Reservation(
                index.getAndIncrement(),
                request.get("name"),
                request.get("date"),
                request.get("time")
        );

        reservations.add(reservation);
        return ResponseEntity
                .created(URI.create("/reservations/"+reservation.getId()))
                .body(reservation);

    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        reservations.removeIf(
                reservation ->
                        reservation.getId().equals(id)
        );
        return ResponseEntity.noContent().build();
    }
}
