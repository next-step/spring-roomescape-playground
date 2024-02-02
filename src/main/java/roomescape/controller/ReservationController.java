package roomescape.controller;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;
import roomescape.exception.ValidateReservationDTO;
import roomescape.dto.ReservationRequestDto;
import roomescape.repository.ReservationRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.repository.TimeRepository;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

        private final ReservationRepository reservationRepository;
        private final TimeRepository timeRepository;

        public ReservationController(ReservationRepository reservationRepository, TimeRepository timeRepository) {
            this.reservationRepository = reservationRepository;
            this.timeRepository = timeRepository;
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
            Time time = timeRepository.findTimeById(reservationDTO.timeId())
                    .orElseThrow(() -> new NotFoundTimeException("Time with id " + reservationDTO.timeId() + " not found"));
            return ResponseEntity.created(URI.create("/reservations/" + reservationDTO.timeId())).body(reservationDTO.toEntity(time));
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
            Reservation reservation = reservationRepository.findReservationById(id)
                    .orElseThrow(() -> new NotFoundReservationException("Reservation with id " + id + " not found"));
            reservationRepository.delete(id);
            return ResponseEntity.noContent().build();
        }


}
