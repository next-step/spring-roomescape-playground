package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import roomescape.DBService;
import roomescape.DTO.ReservationDTO;
import roomescape.domain.Reservation;
import roomescape.controller.exception.InvalidReservationException;
import roomescape.controller.exception.NotFoundReservationException;
import roomescape.domain.value.Date;
import roomescape.domain.value.ID;
import roomescape.domain.value.Name;
import roomescape.domain.value.Time;
import roomescape.DBService;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Controller
public class RoomescapeController {

    private final DBService dbService;

    public RoomescapeController(DBService dbService) {
        this.dbService = dbService;
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationDTO> getReservations() {
        List<Reservation> reservations = dbService.getAllReservations();
        return reservations.stream()
                .map(reservation -> new ReservationDTO(reservation.getID(), reservation.getName(), reservation.getDate(), reservation.getTime()))
                .collect(Collectors.toList());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> addReservation(@Valid @RequestBody ReservationDTO reservationDTO, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            throw new InvalidReservationException(errorMessage);
        }
        Reservation newReservation = dbService.addReservation(new Reservation(reservationDTO.getName(), reservationDTO.getDateToString(), reservationDTO.getTimeToString()));
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.getID()))
                .body(newReservation.toDTO());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        dbService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}
