package roomescape.Controller;


import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Exception.BadRequestReservation;
import roomescape.Exception.NotFoundReservationException;
import roomescape.Model.RequestReservation;
import roomescape.Model.Reservation;

import java.net.URI;
import java.util.*;
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
        String name=reservation.getName();
        String date=reservation.getDate();
        String time=reservation.getTime();


        if (name.isEmpty() || date.isEmpty() || time.isEmpty())
            throw new BadRequestReservation("이름, 날짜, 시간을 모두 입력하세요.");

        Reservation newReservation=new Reservation(atomicLong.incrementAndGet(), name, date, time);
        reservations.add(newReservation);
        URI location=URI.create("/reservations/"+newReservation.getId());

        return ResponseEntity.created(location).body(newReservation);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> reservationResult(@PathVariable Long id){
        for(Reservation reservation:reservations)
            if(reservation.getId()==id)
                return ResponseEntity.ok(reservation);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {

       boolean res=reservations.removeIf(reservation -> reservation.getId()==id);
       if(!res)
            throw new NotFoundReservationException("예약을 찾을 수 없습니다.");
        return ResponseEntity.noContent().build();
    }
}
