package roomescape.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private Long index = 0L;

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }
    public Reservation save(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("예약 정보가 없습니다.");
        }

        if (reservation.getId() != null && reservations.containsKey(reservation.getId())) {
            throw new IllegalArgumentException("이미 등록된 예약입니다.");
        }
        reservation.setId(++index);
        reservations.put(reservation.getId(), reservation);

        return reservation;
    }

    public Reservation findById(Long id) {
        return reservations.get(id);
    }

    public void deleteById(Long id) {
        reservations.remove(id);
    }
}
