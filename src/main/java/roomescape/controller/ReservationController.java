package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.JdbcReservationRepository;

import java.net.URI;
import java.util.List;

@Controller
@ResponseBody // @Controller + @ResponseBody = @RestController
public class ReservationController {

    private final JdbcReservationRepository jdbcReservationRepository;

    public ReservationController(JdbcReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> getReservations() {
        return jdbcReservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody ReservationRequest request
    ) {
        Reservation saved = jdbcReservationRepository.save(request.toReservation());

        return ResponseEntity
                .created(URI.create("/reservations/" + saved.getId()))
                .body(ReservationResponse.from(saved));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {
        int deleted = jdbcReservationRepository.deleteById(id);
        if (deleted == 0) {
            throw new NotFoundReservationException("예약을 찾을 수 없습니다. id=" + id);
        }

        return ResponseEntity.noContent().build();
    }
}
