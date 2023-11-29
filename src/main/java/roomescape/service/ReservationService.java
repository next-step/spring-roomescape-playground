package roomescape.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import roomescape.dto.Reservation;

@Service
public class ReservationService {
    private final Map<Long, Reservation> reservations = new LinkedHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    public List<Reservation> getAllReservations() {
        return reservations.values().stream().toList();
    }

    public Reservation addReservation(Reservation reservation) {
        validateAddReservation(reservation);
        var saved = new Reservation(index.get(), reservation.name(), reservation.date(), reservation.time());
        reservations.put(index.getAndIncrement(), saved);
        return saved;
    }

    private void validateAddReservation(Reservation reservation) {
        if (reservation == null || reservation.name().isEmpty() || reservation.date().isEmpty() || reservation.time().isEmpty()) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
    }

    public void deleteReservation(Long id) {
        validateDeleteReservation(id);
        reservations.remove(id);
    }

    private void validateDeleteReservation(Long id) {
        if (!reservations.containsKey(id)) {
            throw new IllegalArgumentException("존재하지 않는 id입니다.");
        }
    }
}
