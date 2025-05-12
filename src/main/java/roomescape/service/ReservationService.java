package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public Reservation add(Reservation reservation) {
        Reservation newReservation = new Reservation(
                (int) index.getAndIncrement(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );

        boolean isDuplicate = reservations.stream().anyMatch(r ->
                r.getName().equals(reservation.getName()) &&
                        r.getDate().equals(reservation.getDate()) &&
                        r.getTime().equals(reservation.getTime())
        );
        if (isDuplicate) {
            throw new InvalidReservationException("동일한 예약이 이미 존재합니다.");
        }

        reservations.add(newReservation);
        return newReservation;
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
