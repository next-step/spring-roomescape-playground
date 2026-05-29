package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.exception.NotFoundReservationException;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        Reservation reservation =
                new Reservation(
                        null,
                        reservationRequest.name(),
                        reservationRequest.date(),
                        reservationRequest.time()
                );

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        int affectedRows = reservationRepository.delete(id);

        if (affectedRows == 0) {
            throw new NotFoundReservationException();
        }
    }
}
