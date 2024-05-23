package roomescape.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {
    private Map<Long, Reservation> reservations = new HashMap<>();
    private AtomicLong index= new AtomicLong(0);

    public List<Reservation> findAll(){
        return List.copyOf(reservations.values());
    }

    public Reservation save(Reservation reservation){
        reservation.setId(index.incrementAndGet());
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    public void deleteByID(Long id){
        if (!reservations.containsKey(id)) {
            throw new NotFoundReservationException("존재하지 않는 예약정보 입니다.");
        }
        reservations.remove(id);
    }
}
