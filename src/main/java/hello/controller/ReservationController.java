package hello.controller;

import hello.controller.dto.CreateReservationDto;
import hello.repository.ReservationRepository;
import hello.controller.dto.ReservationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> reservationList() {

        List<ReservationDto> Reservations = reservationRepository.findAllReservations()
                .stream()
                .map(ReservationDto::toDto)
                .toList();

        return ResponseEntity.ok(Reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> addReservation(@Validated @RequestBody CreateReservationDto dto) {
        ReservationDto savedReservation = ReservationDto.toDto(reservationRepository.save(dto));
        return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId())).body(savedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable("id") Long id) {

        reservationRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
