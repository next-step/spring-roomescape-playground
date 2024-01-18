package roomescape.controller;
import roomescape.Entity.Reservation;
import roomescape.Entity.Reservations;
import roomescape.Exception.NotFoundReservationException;
import roomescape.Exception.ValidateReservationDTO;
import roomescape.DTO.ReservationDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
        private final Reservations reservations = new Reservations();
        private AtomicLong index = new AtomicLong(1);

        @GetMapping
        public ResponseEntity<List<ReservationDTO>> readReservations() {
            List<ReservationDTO> reservationDTOs = reservations.getAll().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(reservationDTOs);
        }

        @PostMapping
        public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
            ValidateReservationDTO.validateReservation(reservationDTO);
            Reservation reservation = new Reservation(index.getAndIncrement(), reservationDTO.getName(), reservationDTO.getDate(), reservationDTO.getTime());
            reservations.add(reservation);
            return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(convertToDto(reservation));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Void> updateReservation(@RequestBody Reservation newReservation, @PathVariable long id) {
            Reservation reservation = reservations.findById(id)
                    .orElseThrow(() -> new NotFoundReservationException("Reservation with id " + id + " not found"));
            reservation.update(newReservation);
            return ResponseEntity.ok().build();
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
            Reservation reservation = reservations.findById(id)
                    .orElseThrow(() -> new NotFoundReservationException("Reservation with id " + id + " not found"));
            reservations.remove(reservation);
            return ResponseEntity.noContent().build();
        }

        private ReservationDTO convertToDto(Reservation reservation) {
            return new ReservationDTO(reservation.getName(), reservation.getDate(), reservation.getTime());
        }

}
