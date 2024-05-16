package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.exception.ErrorMessage;
import roomescape.exception.ReservationException;
import roomescape.model.Reservation;

@Repository
public class ReservationRepository {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong idMaker = new AtomicLong();

    public List<Reservation> findAll() {
        return List.copyOf(reservations.values()); // 복사본이 원본의 변경에 영향을 받지 않도록 copyOf()를 사용
    }

    public void save(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    public void deleteById(Long id) {
        if (!reservations.containsKey(id)) {
            throw new ReservationException(ErrorMessage.INVALID_ID_REQUEST);
        }
        reservations.remove(id);
    }

}
