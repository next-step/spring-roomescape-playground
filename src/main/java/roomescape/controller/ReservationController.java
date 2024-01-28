package roomescape.controller;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.ValidateReservationDTO;
import roomescape.dto.ReservationDto;
import roomescape.repository.ReservationRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

        @Autowired
        private ReservationRepository reservationRepository;

        @GetMapping
        public ResponseEntity<List<ReservationDto>> readReservations() {
            List<ReservationDto> reservationDtos = reservationRepository.findAllReservations().stream()
                    .map(ReservationDto::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(reservationDtos);
        }

        @PostMapping
        public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDTO) {
            ValidateReservationDTO.validateReservation(reservationDTO);
            long newId = reservationRepository.insertWithKeyHolder(reservationDTO);
            Reservation reservation = new Reservation(newId, reservationDTO.getName(), reservationDTO.getDate(), reservationDTO.getTime());
            return ResponseEntity.created(URI.create("/reservations/" + newId)).body(reservation);
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
            Reservation reservation = reservationRepository.findReservationById(id)
                    .orElseThrow(() -> new NotFoundReservationException("Reservation with id " + id + " not found"));
            reservationRepository.delete(id);
            return ResponseEntity.noContent().build();
        }


}
