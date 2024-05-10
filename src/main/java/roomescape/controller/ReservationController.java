package roomescape.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationResponseDto;
import roomescape.mapper.DtoMapper;
import roomescape.model.Reservation;

@Controller
public class ReservationController {

    private List<Reservation> reservations;
    private final AtomicLong idMaker = new AtomicLong();

    public ReservationController() {
        this.reservations = createReservations();
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponseDto> getReservations() {
        return reservations.stream()
                .map(DtoMapper::convertToDTO)
                .toList();
    }

    private List<Reservation> createReservations() {
        final List<String> names = List.of("해쉬", "브라운", "버거");
        return names.stream()
                .map(name -> new Reservation(idMaker.incrementAndGet(), name))
                .toList()
        ;
    }

}
