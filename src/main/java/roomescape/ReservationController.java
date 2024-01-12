package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
//    주석처리 - 5단계 이전 코드
//    private final List<Reservation> reservations = new ArrayList<>();
//    private final AtomicLong index = new AtomicLong(1);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 생성자 내 주석 코드 - 2단계 테스트를 위한 임의 data 추가(.body("size()", is(3)))
    public ReservationController() {
//        create(new Reservation("브라운","2023-01-01", "10:00"));
//        create(new Reservation("브라운","2023-01-02", "11:00"));
//        create(new Reservation("브라운","2023-01-03", "12:00"));
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);

        if(Reservation.checkValidity(reservation)) throw new NoParameterException();
        else {
//            reservations.add(newReservation);

            Long id = reservationDAO.insertNewReservation(reservation);
            Reservation newReservation = Reservation.toEntity(reservation, id);
            return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
        }
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
//        return ResponseEntity.ok().body(reservations);
        return ResponseEntity.ok().body(new ReservationDAO(jdbcTemplate).findAllReservations());
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Void> update(@RequestBody Reservation newReservation, @PathVariable Long id) {
        ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);

        Reservation reservation = reservationDAO.findAllReservations().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

//        reservation.update(newReservation);

        reservationDAO.insertNewReservation(newReservation);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);

        Reservation reservation = reservationDAO.findAllReservations().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

//        reservations.remove(reservation);

        reservationDAO.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
