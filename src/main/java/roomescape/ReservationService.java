package roomescape;

import java.util.List;
import org.springframework.stereotype.Service;
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
        reservationRequest.validate();
        Reservation newReservation = Reservation.create(
            reservationRequest.name(),
            reservationRequest.date(),
            reservationRequest.time()
        );
        long id = reservationRepository.createReservation(newReservation);
        return newReservation.withId(id);
    }

    public int deleteReservation(long id) {
        int deletedCount = reservationRepository.deleteReservation(id);
        if (deletedCount == 0) {
            throw new NotFoundException("Reservation not found: id=" + id);
        }
        return deletedCount;
    }
}
