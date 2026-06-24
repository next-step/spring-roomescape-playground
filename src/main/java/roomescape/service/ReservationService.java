package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservation() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(
                null,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
        long id = reservationRepository.insert(reservation);
        return new Reservation(
                id,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
    }

    public void cancelReservation(Long id) {
        boolean isRemoved = reservationRepository.delete(id);

        if (!isRemoved) {
            throw new NotFoundReservationException();
        }
    }

}
