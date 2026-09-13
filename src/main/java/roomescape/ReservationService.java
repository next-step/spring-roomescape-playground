package roomescape;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.exception.NotFoundException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository,
        TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> read() {
        return reservationRepository.read();
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        reservationRequest.validate();
        Time time = timeRepository.findTimeById(reservationRequest.getTime());

        Reservation newReservation = Reservation.create(
            reservationRequest.name(),
            reservationRequest.date(),
            time
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
