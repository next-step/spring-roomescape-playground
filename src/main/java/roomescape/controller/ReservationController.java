package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationDto;
import roomescape.service.ReservationService;

import java.util.List;
import java.util.Objects;

@Controller
public class ReservationController {


    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }


    // @PostMapping("/reservations")
    // public ResponseEntity<?> create(@RequestBody ReservationDto reqeust){
    //     if (reqeust.getName().isEmpty() || reqeust.getDate().isEmpty() || reqeust.getTime().isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("예약 추가 시 필요한 인자값이 비어있습니다.");
    //     }
    //     Reservation reservation = new Reservation(index.incrementAndGet(), reqeust.getName(), reqeust.getDate(), reqeust.getTime());
    //     reservations.add(reservation);
    //     return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/reservations/"+index.get()).body(reservation);
    // }

    // @GetMapping("/reservations")
    // public ResponseEntity<List<Reservation>> get() {
    //     return ResponseEntity.ok(reservations);
    // }

    // @DeleteMapping("/reservations/{id}")
    // public ResponseEntity<?> delete(@PathVariable(name = "id")Long id) {
    //     if (id <= 0 || id > reservations.size()) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없습니다.");
    //     }
    //     reservations.removeIf(reservation -> Objects.equals(reservation.getId(), id));
    //     return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    // }

    // JDBC
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservation() {
        return reservationService.findAllReservation();
    }

    @PostMapping("/reservations")
    public ResponseEntity<?> create(@RequestBody ReservationDto request){

        return reservationService.createReservation(request);

    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {

        return reservationService.deleteReservation(id);

    }
}
