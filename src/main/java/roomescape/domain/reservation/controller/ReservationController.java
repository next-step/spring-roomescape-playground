package roomescape.domain.reservation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import roomescape.domain.reservation.dto.request.CreateReservationRequest;
import roomescape.domain.reservation.dto.request.CreateTimeRequest;
import roomescape.domain.reservation.dto.response.GetReservationResponse;
import roomescape.domain.reservation.dto.response.GetTimesResponse;
import roomescape.domain.reservation.entity.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.entity.Time;
import roomescape.domain.reservation.service.ReservationService;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
@Validated
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("home")
    public String getHome() {
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "new-reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<GetReservationResponse>> getReservations() {
        return ResponseEntity.ok(reservationService.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> createReservation(@RequestBody @Valid CreateReservationRequest requestDto) {
        final Reservation savedReservation = reservationService.saveReservation(requestDto.getName(), requestDto.getDate(), requestDto.getTimeId());
        return ResponseEntity.status(CREATED)
                .location(URI.create("/reservations/" + savedReservation.getId()))
                .build();
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable @PositiveOrZero long reservationId) {
        final Reservation reservation = reservationService.findReservation(reservationId);
        reservationService.deleteReservation(reservation.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/times")
    public ResponseEntity<List<GetTimesResponse>> findAllTimes() {
        return ResponseEntity.ok(reservationService.findAllTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<Void> createTime(@RequestBody @Valid CreateTimeRequest request) {
        final Time savedTime = reservationService.saveTime(request.getTime());
        return ResponseEntity.status(CREATED)
                .location(URI.create("/times/" + savedTime.getId()))
                .build();
    }

    @DeleteMapping("/times/{timeId}")
    public ResponseEntity<Void> deleteTimes(@PathVariable @PositiveOrZero long timeId) {
        final long findTimesId = reservationService.findTimes(timeId);
        reservationService.deleteTimes(findTimesId);
        return ResponseEntity.noContent().build();
    }

}
