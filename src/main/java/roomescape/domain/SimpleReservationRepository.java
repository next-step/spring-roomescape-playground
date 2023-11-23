package roomescape.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SimpleReservationRepository implements ReservationRepository {

    private AtomicLong index = new AtomicLong(0);

    private List<Reservation> reservations = new ArrayList<>();

    @Override
    public Reservation save(final Reservation reservation) {
        final Reservation savedReservation = new Reservation(index.incrementAndGet(), reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(savedReservation);
        return savedReservation;
    }

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public void delete(final Long id) {
        final Reservation reservation = findById(id);
        reservations.remove(reservation);
    }

    @Override
    public Reservation findById(final Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 예약이 존재하지 않습니다."));
    }
}
