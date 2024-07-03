package roomescape.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.RequestReservation;
import roomescape.exception.BadRequestException;
import roomescape.model.Reservation;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservation")
    public String reserve() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reserveList() {
        return ResponseEntity.ok().body(reservations);
    }

    private void validateRequestReservation(RequestReservation requestReservation) {
        if (requestReservation.name() == null || requestReservation.name().isEmpty()) {
            throw new BadRequestException("이름을 작성해주세요");
        }
        if (requestReservation.date() == null || requestReservation.date().isEmpty()) {
            throw new BadRequestException("날짜를 선택해주세요");
        }
        if (requestReservation.time() == null || requestReservation.time().isEmpty()) {
            throw new BadRequestException("시간을 선택해주세요");
        }
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> reservation(@RequestBody RequestReservation requestReservation) {
        validateRequestReservation(requestReservation);

        Reservation newReservation = Reservation.of(requestReservation, index.getAndIncrement());

        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("해당하는 예약을 찾을 수 없습니다"));

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }

}
