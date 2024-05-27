package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryReservationRepository implements ReservationRepository {
    private static int id = 0;
    private static final Map<Integer, Reservation> reservationStore = new HashMap<>();

    @Override
    public Reservation reservationAdd(Reservation reservation) {
        reservation.setId(++id);
        reservationStore.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservationStore.values());
    }

    @Override
    public void delete(int id) {
        if (reservationStore.containsKey(id)) {
            reservationStore.remove(id);
        }
    }
}
