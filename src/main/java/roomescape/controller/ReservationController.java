package roomescape.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.ReservationRequestDTO;
import roomescape.model.ReservationResponseDTO;
import roomescape.repository.service.ReservationService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @ResponseBody
    @GetMapping
    public List<ReservationResponseDTO> allReservationsController() {
        List<ReservationResponseDTO> reservations = reservationService.findAll();
        log.info("reservations = {}", reservations);
        return reservations;
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<ReservationResponseDTO> reservationAddController(@Valid @RequestBody ReservationRequestDTO reservation) {
        ReservationResponseDTO responseDTO = reservationService.reservationAdd(reservation);

        HttpHeaders headers = new HttpHeaders();
        String uri = "/reservations/" + responseDTO.getId();
        headers.setLocation(URI.create(uri));
        ResponseEntity<ReservationResponseDTO> response = new ResponseEntity<>(responseDTO, headers, HttpStatus.CREATED);

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteController(@PathVariable int id) {
        reservationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
