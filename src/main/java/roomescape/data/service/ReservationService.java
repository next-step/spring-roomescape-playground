package roomescape.data.service;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import roomescape.data.dao.daoInterface.ReservationDao;
import roomescape.data.dto.ReservationRequest;
import roomescape.data.dto.ReservationResponse;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    public List<ReservationResponse> getReservations() {
        return reservationDao.getReservations();
    }

    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        long newReservationId = reservationDao.createReservation(reservationRequest);
        URI location = ServletUriComponentsBuilder.fromPath("/reservations/" + newReservationId)
                .build()
                .toUri();
        ReservationResponse reservationResponse = new ReservationResponse(newReservationId, reservationRequest.getName(), reservationRequest.getDate(),
                reservationRequest.getTime());
        return ResponseEntity.created(location).body(reservationResponse);
    }

    public ResponseEntity<?> deleteReservation(@PathVariable Long deletedReservationId) {
        reservationDao.deleteReservation(deletedReservationId);
        return ResponseEntity.noContent().build();
    }
}
