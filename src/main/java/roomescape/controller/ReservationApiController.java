package roomescape.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.db.ReservationEntity;
import roomescape.model.ReservationRequest;
import roomescape.service.QueryingDAO;
import roomescape.service.UpdatingDAO;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequiredArgsConstructor
public class ReservationApiController {

    private final QueryingDAO queryingDAO;
    private final UpdatingDAO updatingDAO;

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationEntity>> reservations() {

        List<ReservationEntity> lists = queryingDAO.findAllList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(lists);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationEntity> createReservation(
            @Valid
            @RequestBody ReservationRequest reservationRequest
    ) {

        Long nowId = Long.valueOf(queryingDAO.count()) + 1;

        updatingDAO.insert(reservationRequest);

        ReservationEntity reservationEntity = queryingDAO.findReservationById(nowId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/reservations/" + Long.toString(nowId)));

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(reservationEntity);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {

        updatingDAO.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
