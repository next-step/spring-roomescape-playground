package roomescape.domain.reservation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.dto.request.ReservationCreateRequestDto;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.reservation.repository.ReservationDao;
import roomescape.domain.time.entity.Time;
import roomescape.domain.time.repository.TimeDao;
import roomescape.exception.custom.BusinessException;

import java.net.URI;
import java.util.List;

import static roomescape.exception.ErrorCode.RESERVATION_NOT_FOUND;

@Controller
public class ReservationController {
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationController(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readReservations() {
        return ResponseEntity.ok().body(reservationDao.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid ReservationCreateRequestDto requestDto) {
        System.out.println(requestDto.name() + " " + requestDto.date() + " " + requestDto.timeId());
        Long reservationId = reservationDao.insert(requestDto);
        Time findTime = timeDao.findTimeById(Long.parseLong(requestDto.timeId()));
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).body(requestDto.toEntity(reservationId, findTime));
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