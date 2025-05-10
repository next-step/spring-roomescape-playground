package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public Reservation add(ReservationRequest request) {
        Reservation reservation = request.makeValidReservation((int) index.getAndIncrement());

        if (reservations.contains(reservation)) {
            throw new InvalidReservationException("동일한 예약이 이미 존재합니다.");
        }

        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> findAll() {
        return reservations;
    }

    public void delete(int id) {
        boolean removed = reservations.removeIf(r -> r.getId() == id);
        if (!removed) {
            throw new NotFoundReservationException("해당 ID가 없습니다.");
        }
    }
}
