package roomescape.reservation.repository;

import org.springframework.stereotype.Repository;
import roomescape.reservation.model.Reservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {
    private static final Map<Long,Reservation> store = new HashMap<>();
    private final AtomicLong index = new AtomicLong(0);

    public Reservation save(Reservation reservation) {
        reservation.setId(index.incrementAndGet());
        store.put(reservation.getId(), reservation);

        return reservation;
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(store.values());
    }

    public Reservation findById(Long id) {
        return Optional.ofNullable(store.get(id)).orElse(store.get(id));
    }
    public void deleteById(final Long id) {
        store.remove(id);
    }
//    public void update (Long id, Reservation updateReservation) {
//        Reservation findReservation = findById(id);
//
//        findReservation.setName(updateReservation.getName());
//        findReservation.setDate(updateReservation.getDate());
//        findReservation.setTime(updateReservation.getTime());
//    }

}
