package roomescape;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.exception.NotFoundException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> read() {
        return reservationRepository.read();
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {

        long id = reservationRepository.createReservation(reservationRequest);
        Reservation reservation = Reservation.create(
            id,
            reservationRequest.name(),
            reservationRequest.date(),
            reservationRequest.time()
        );
        return reservation;
    }

    public int deleteReservation(long id) {
        int deletedCount = reservationRepository.deleteReservation(id);
        if (deletedCount == 0) {
            throw new NotFoundException("Reservation not found: id=" + id);
        }
        return deletedCount;
    }
}
