package roomescape.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationDTO;
import roomescape.entity.Reservation;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Slf4j
@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        // TODO: 저장된 모든 reservation 정보를 반환한다.
        return ResponseEntity.ok(reservationService.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationDTO reservationDTO) {
        // TODO: reservation 정보를 받아서 생성한다.
        Reservation reservation = reservationService.saveReservation(reservationDTO);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO: url 상의 id 정보를 받아 reservation을 삭제한다.
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

}