package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.BadRequestException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;


    public ReservationController(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "new-reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> showReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody @Valid ReservationRequest request) {
        Time time = timeRepository.findById(request.timeId())
                .orElseThrow(() -> new BadRequestException("시간이 존재하지 않습니다."));

        Reservation reservation = new Reservation(
                null,
                request.name(),
                request.date(),
                time
        );

        validateReservationDateTime(reservation);

        Reservation savedReservation = reservationRepository.save(reservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + savedReservation.getId()))
                .body(ReservationResponse.from(savedReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean deleted = reservationRepository.deleteById(id);

        if (!deleted) {
            throw new BadRequestException("예약번호가 " + id + "인 예약은 존재하지 않습니다.");
        }

        return ResponseEntity.noContent().build();
    }

    private void validateReservationDateTime(Reservation reservation) {
        LocalDateTime reservationDateTime = LocalDateTime.of(
                reservation.getDate(),
                reservation.getTime().getTime()
        );

        if (reservationDateTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("예약 시간은 현재 시각 이후여야 합니다.");
        }
    }
}