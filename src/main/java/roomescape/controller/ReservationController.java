package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.controller.dto.ReservationDto;
import roomescape.controller.dto.ReservationSaveRequestDto;
import roomescape.controller.dto.ReservationSaveResponseDto;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;

    public ReservationController(ReservationRepository reservationRepository, ReservationTimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        return ResponseEntity.ok()
                .body(ReservationDto.from(reservations));
    }

    @PostMapping
    public ResponseEntity<ReservationSaveResponseDto> createReservations(@Valid @RequestBody ReservationSaveRequestDto request) {
        ReservationTime time = timeRepository.findById(request.timeId());

        Reservation reservation = new Reservation(request.name(), request.date(), time);
        long id = reservationRepository.save(reservation);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + id)
                .body(new ReservationSaveResponseDto(id));
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "deleteId") long deleteId) {
        reservationRepository.deleteById(deleteId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
