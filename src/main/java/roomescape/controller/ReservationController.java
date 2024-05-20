package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationDTO;
import roomescape.exception.InvalidReservationInputException;
import roomescape.exception.InvalidReservationTimeException;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String getReservationPage(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationDTO> getReservations() {
        return reservationRepository.findAll();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> addReservation(@RequestBody ReservationDTO reservation) {
        if (reservation.name().isBlank() || reservation.date().isBlank() || reservation.time().isBlank()) {
            throw new InvalidReservationInputException("필수 입력값이 비어있습니다.", reservation.toString());
        }

        LocalDateTime reservationDateTime = LocalDateTime.parse(reservation.date() + "T" + reservation.time());
        if (reservationDateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidReservationTimeException("현재 시간 이전으로는 예약할 수 없습니다.");
        }

        List<ReservationDTO> existingReservations = reservationRepository.findAll();
        if (existingReservations.stream().anyMatch(r -> r.date().equals(reservation.date()) && r.time().equals(reservation.time()))) {
            throw new InvalidReservationTimeException("이미 예약된 시간입니다.");
        }

        ReservationDTO newReservation = reservationRepository.save(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.id())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        Optional<ReservationDTO> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty()) {
            throw new ReservationNotFoundException(id);
        }
        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
