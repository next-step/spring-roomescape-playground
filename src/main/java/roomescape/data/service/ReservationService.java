package roomescape.data.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import roomescape.common.exception.ReservationErrorCode;
import roomescape.common.exception.ReservationException;
import roomescape.data.dto.ReservationRequest;
import roomescape.data.dto.ReservationResponse;
import roomescape.data.entity.Reservation;

@Service
public class ReservationService {

    private List<Reservation> reservationList = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    public List<ReservationResponse> getReservations() {
        return reservationList.stream().map(Reservation::createResponse).collect(Collectors.toList());
    }

    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = Reservation.create(index.incrementAndGet(), reservationRequest.getName(), reservationRequest.getDate(), reservationRequest.getTime());
        reservationList.add(newReservation);
        URI location = ServletUriComponentsBuilder.fromPath("/reservations/" + newReservation.getId())
                .build()
                .toUri();
        return ResponseEntity.created(location).body(newReservation.createResponse());
    }

    public ResponseEntity deleteReservation(@PathVariable String deletedReservationId) {
        long idToDelete = Long.parseLong(deletedReservationId);
        Optional<Reservation> reservationToRemove = reservationList.stream()
                .filter(reservation -> reservation.getId() == idToDelete)
                .findFirst();
        if (reservationToRemove.isPresent()) {
            reservationList.remove(reservationToRemove.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        throw new ReservationException(ReservationErrorCode.NO_DELETE_RESERVATION_FOUND);
    }
}
