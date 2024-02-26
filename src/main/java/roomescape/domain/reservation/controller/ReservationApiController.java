package roomescape.domain.reservation.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.dto.ReservationCreateDTO;
import roomescape.domain.reservation.dto.ReservationResponseDTO;
import roomescape.domain.reservation.mapper.ReservationMapper;
import roomescape.domain.reservation.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {
    private final ReservationService reservationService;

    public ReservationApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> addReservation(@RequestBody @Valid ReservationCreateDTO reservationCreateDTO) {
       Reservation newReservation = reservationService.addReservation(ReservationMapper.toEntity(reservationCreateDTO));
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(ReservationMapper.toReservationResponseDTO(newReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> reservations(@PathVariable Long id) {
        return ResponseEntity.ok().body(ReservationMapper.toReservationResponseDTO(reservationService.getReservationById(id)));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> reservations() {

        List<ReservationResponseDTO> reservationResponseDTOS = reservationService.getAllReservation().stream()
        .map(ReservationMapper::toReservationResponseDTO)
        .collect(Collectors.toList());

        return ResponseEntity.ok().body(reservationResponseDTOS);
    }
}
