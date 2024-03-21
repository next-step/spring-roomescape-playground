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
import roomescape.service.ReservationService;

import java.util.List;

@RequestMapping("/reservations")
@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAll();

        return ResponseEntity.ok()
                .body(ReservationDto.from(reservations));
    }

    @PostMapping
    public ResponseEntity<ReservationSaveResponseDto> createReservations(@Valid @RequestBody ReservationSaveRequestDto request) {
        long id = reservationService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + id)
                .body(new ReservationSaveResponseDto(id));
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "deleteId") long deleteId) {
        reservationService.deleteById(deleteId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
