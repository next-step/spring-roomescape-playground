package roomescape.Controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Domain.ReservationDomain;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private AtomicLong index = new AtomicLong(1);
    private List<ReservationDomain> reservationDomains = new ArrayList<>();

    @GetMapping("/") // index로 이름을 바꾸어 처리했는데, @Controller 사용해서 수정
    public String Home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String Reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDomain>> Reservations() {
        return ResponseEntity.ok(reservationDomains);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDomain> createReservation(@RequestBody ReservationDomain reservationDomain)
    {
        if(reservationDomain == null || !reservationDomain.isValid()) {
            throw new IllegalArgumentException("누락된 사항이 있습니다. 확인해주세요.");
        }

        ReservationDomain newReservation = ReservationDomain.toEntity(reservationDomain, index.getAndIncrement());
        reservationDomains.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id)
    {
        ReservationDomain reservationDomain = reservationDomains.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("삭제할 유저가 없습니다."));

        reservationDomains.remove(reservationDomain);

        return ResponseEntity.noContent().build();
    }
}
