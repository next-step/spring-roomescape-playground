package roomescape.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.mapper.DtoMapper;
import roomescape.model.Reservation;

@Controller
public class ReservationController {

    private List<Reservation> reservations;
    private final AtomicLong idMaker = new AtomicLong();

    public ReservationController() {
        this.reservations = new ArrayList<>(createReservations());
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

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationResponseDto> saveReservation(@RequestBody ReservationRequestDto dto) {
        final Reservation reservation = new Reservation(
                idMaker.incrementAndGet(),
                dto.getName(),
                dto.getDate(),
                dto.getTime());
        reservations.add(reservation);
        final ReservationResponseDto responseDto = DtoMapper.convertToDTO(reservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + responseDto.getId()))
                .body(responseDto);
    }

    private List<Reservation> createReservations() {
        return List.of(
                new Reservation(idMaker.incrementAndGet(), "해쉬", LocalDate.now(), LocalTime.now()),
                new Reservation(idMaker.incrementAndGet(), "브라운", LocalDate.now(), LocalTime.now()),
                new Reservation(idMaker.incrementAndGet(), "버거", LocalDate.now(), LocalTime.now())
        );
    }

}
