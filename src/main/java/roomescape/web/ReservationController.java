package roomescape.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;
import roomescape.web.exception.NotFoundReservationException;
import roomescape.web.dto.ReservationDto;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Map;



@RestController
@RequestMapping("/reservations")
public class ReservationController {

  private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationDto> getAllReservation() {
        return reservationService.getAllReservation();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReservationDto> create(@RequestBody Map<String, String> params) {

        String name = params.get("name");
        String date = params.get("date");
        String time = params.get("time");

        if(name == null || date == null || time ==null) {
            throw new NotFoundReservationException("필요한 인자가 부족합니다.");
        }
        
        ReservationDto newReservation = reservationService.createReservation(name, date, time);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newReservation.getId())
                .toUri();

        return ResponseEntity.created(location).body(newReservation);

        }


    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> read(@PathVariable Long id) {

        Optional<Reservation> reservationDtoOptional = reservationService.getReservationById(id);

        if (reservationDtoOptional.isPresent()) {
            ReservationDto reservationDto = new ReservationDto(reservationDtoOptional.get());
            return ResponseEntity.ok(reservationDto);
        } else {
            throw new NotFoundReservationException("예약을 찾을 수 없습니다.");
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservationById(id);
        return ResponseEntity.noContent().build();
    }
}

