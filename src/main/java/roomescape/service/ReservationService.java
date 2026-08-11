package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteById(Long id) {
        if (!reservationRepository.deleteById(id)) {
            throw new NotFoundReservationException(id);
        }
    }
}
