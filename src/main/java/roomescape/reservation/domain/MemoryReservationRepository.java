package roomescape.reservation.domain;

import org.springframework.stereotype.Repository;
import roomescape.reservation.exception.exception.NotFoundReservationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class MemoryReservationRepository {
    private static final HashMap<Long, Reservation> store = new HashMap<Long, Reservation>();
    private static Long sequence = 0L;


    public Reservation save(Reservation reservation) {
        reservation.setId(++sequence);
        store.put(reservation.getId(),reservation);
        return reservation;
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(store.values());
    }

    public Reservation deleteReservation(Long id){
        if (store.containsKey(id)) {
            Reservation removeReservation = store.get(id);
            store.remove(id);
            return removeReservation;
        }
        throw new NotFoundReservationException("해당 id를 찾지 못하였습니다.");
    }
}
