package hello.controller;

import hello.controller.dto.CreateReservationDto;
import hello.exceptions.NotFoundReservationException;
import hello.repository.ReservationRepository;
import hello.repository.dto.ReservationDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "/reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationDto> reservationList() {
        return reservationRepository.findAllReservations();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto> addReservation(@Validated @RequestBody CreateReservationDto dto) {

        Long savedId = reservationRepository.save(dto);
        ReservationDto reservation = reservationRepository.findById(savedId);

        return ResponseEntity.created(URI.create("/reservations/" + savedId)).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable("id") Long id) {

        try {
            reservationRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundReservationException();
        }

        reservationRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
