package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationDTO;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;


@Controller
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationDTO>> read() {
        List<ReservationDTO> reservationDTOs = reservationService.getAllReservations();
        return ResponseEntity.ok().body(reservationDTOs);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationDTO> create(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO newReservationDTO = reservationService.insertReservation(reservationDTO);
        return ResponseEntity.created(URI.create("/reservations/" + newReservationDTO.getId())).body(newReservationDTO);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservationById(id);
        return ResponseEntity.noContent().build();
    }
}
