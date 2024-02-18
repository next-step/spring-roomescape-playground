package roomescape.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.Domain.ReservationDomain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ReservationController {
    private List<ReservationDomain> reservationDomains = new ArrayList<>
            (
                    Arrays.asList
                            (
                                    new ReservationDomain(1L, "서정빈", LocalDate.parse("2024-02-18"), LocalTime.of(12, 0)),
                                    new ReservationDomain(2L, "서정빈", LocalDate.parse("2024-02-19"), LocalTime.of(14, 0)),
                                    new ReservationDomain(3L, "서정빈", LocalDate.parse("2024-02-20"), LocalTime.of(16, 0))
                            )
            );

    @GetMapping("/") // index로 이름을 바꾸어 처리했는데, @Controller 사용해서 수정
    public String Home(){ return "home"; }

    @GetMapping("/reservation")
    public String Reservation()
    {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDomain>> Reservations() {
        return ResponseEntity.ok(reservationDomains);
    }
}
