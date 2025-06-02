package roomescape.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(5);

    public RoomescapeController() {
        reservations.add(new Reservation(1L, "오찌", "2025-06-02", "14:00"));
        reservations.add(new Reservation(2L, "희정", "2025-06-02", "16:00"));
        reservations.add(new Reservation(3L, "장순", "2025-06-02", "18:00"));
        reservations.add(new Reservation(4L, "예진", "2025-06-02", "20:00"));
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationPage(Model model) {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return reservations;
    }

    // 예약 추가
    @PostMapping("/reservations")
    @ResponseBody
    public Reservation addReservation(@RequestBody Reservation newReservation) {
        Long newId = index.getAndIncrement();
        Reservation reservation = new Reservation(
                newId,
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime()
        );
        reservations.add(reservation);
        return reservation;
    }

    // 예약 삭제
    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public String deleteReservation(@PathVariable Long id) {
        Optional<Reservation> reservation = reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();

        if (reservation.isPresent()) {
            reservations.remove(reservation.get());
            return "삭제 성공: id=" + id;
        }
        else {
            return id + "번의 예약이 없습니다. 예약에 실패하였습니다.";
        }
    }
}
