package roomescape.domain.reservation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.dto.request.ReservationCreateRequestDto;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.reservation.repository.ReservationDao;
import roomescape.exception.custom.BusinessException;

import java.net.URI;
import java.util.List;

import static roomescape.exception.ErrorCode.RESERVATION_NOT_FOUND;

@Controller
public class ReservationController {
    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readReservations() {
        return ResponseEntity.ok().body(reservationDao.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid ReservationCreateRequestDto requestDto) {
        Long reservationId = reservationDao.insert(requestDto);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).body(requestDto.toEntity(reservationId));
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationDao.findAllReservations().stream()
                .filter(reservation -> reservation.getId().equals(reservationId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(RESERVATION_NOT_FOUND));

        reservationDao.deleteReservationById(reservationId);
        return ResponseEntity.noContent().build();
    }
}