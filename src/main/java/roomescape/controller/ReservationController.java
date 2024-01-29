package roomescape.controller;

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
import roomescape.controller.dto.ReservationSaveDto;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        
        return ResponseEntity.ok()
                .body(ReservationDto.from(reservations));
    }

    @PostMapping
    public ResponseEntity<ReservationSaveDto> createReservations(@RequestBody Map<String, String> request) {
        Reservation reservation = new Reservation(request.get("name"), request.get("date"), request.get("time"));
        long id = reservationRepository.save(reservation);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + id)
                .body(new ReservationSaveDto(id));
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "deleteId") long deleteId) {
        reservationRepository.deleteById(deleteId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
