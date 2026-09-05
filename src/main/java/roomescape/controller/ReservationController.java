package roomescape.controller;

import roomescape.controller.dto.ReservationRequestDto;
import roomescape.controller.dto.ReservationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.service.ReservationService;
import roomescape.exception.NotFoundException;

import java.net.URI;
import java.util.List;



@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> read() {
        return ResponseEntity.ok().body(reservationService.read());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> create(@RequestBody ReservationRequestDto reservationDTO) {
        ReservationResponseDto responseDto = reservationService.create(reservationDTO);
        return ResponseEntity.created(URI.create("/reservations/" + responseDto.getId())).body(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}

