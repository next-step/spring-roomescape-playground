package roomescape.domain.reservation.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.global.BusinessException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static roomescape.global.ErrorCode.*;

@Repository
public class ReservationRepositoryPojo {

    private static final Map<Long, Reservation> db = new HashMap<>();

    private static AtomicLong id = new AtomicLong(1);

    public void save(Reservation reservation) {
        db.put(id.getAndIncrement(), reservation);

    }

    public List<Reservation> findAll() {
        return db.values()
                .stream()
                .toList();
    }

    public Reservation findById(Long id) {
        return db.values()
                .stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BusinessException(id, "reservationId", RESERVATION_NOT_FOUND));
    }

    public void deleteById(Long id) {
        List<Long> reservationLogIds = db.entrySet().stream()
                .filter(entry -> entry.getValue().getId().equals(id))
                .map(Map.Entry::getKey)
                .toList();

        reservationLogIds.forEach(db::remove);
    }

    public void clear() {
        db.clear();
        id.set(0);
    }

}
