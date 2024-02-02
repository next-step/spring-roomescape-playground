package roomescape.controller;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.ValidateReservationDTO;
import roomescape.dto.ReservationRequestDto;
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

        private final ReservationRepository reservationRepository;

        public ReservationController(ReservationRepository reservationRepository) {
            this.reservationRepository = reservationRepository;
        }

        @GetMapping
        public ResponseEntity<List<ReservationRequestDto>> readReservations() {
            List<ReservationRequestDto> reservationDtos = reservationRepository.findAllReservations().stream()
                    .map(ReservationRequestDto::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(reservationDtos);
        }

        @PostMapping
        public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequestDto reservationDTO) {
            ValidateReservationDTO.validateReservation(reservationDTO);
            long newId = reservationRepository.insertWithKeyHolder(reservationDTO);
            return ResponseEntity.created(URI.create("/reservations/" + newId)).body(reservationDTO.toEntity(newId));
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
            Reservation reservation = reservationRepository.findReservationById(id)
                    .orElseThrow(() -> new NotFoundReservationException("Reservation with id " + id + " not found"));
            reservationRepository.delete(id);
            return ResponseEntity.noContent().build();
        }


}
