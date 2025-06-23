package roomescape.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationRequest request) {
        long id = index.getAndIncrement();
        return new Reservation(id, request.name(), request.date(),
            request.time());
    }

    public void deleteReservation(long id) {
        Reservation reservation = reservations.remove(id);
        if (reservation == null) {
            throw new ReservationNotFoundException(id);
        }
    }
}
