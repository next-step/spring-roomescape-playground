package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import roomescape.dto.Reservation;

@Service
public class ReservationService {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public List<Reservation> getAllReservations() {
        return reservations.stream()
            .filter(reservation -> reservation.id() != null)
            .toList();
    }

    public Reservation addReservation(Reservation reservation) {
        validateAddReservation(reservation);
        var saved = new Reservation(index.getAndIncrement(), reservation.name(), reservation.date(), reservation.time());
        reservations.add(saved);
        return saved;
    }

    private void validateAddReservation(Reservation reservation) {
        if (reservation == null || reservation.name().isEmpty() || reservation.date().isEmpty() || reservation.time().isEmpty()) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
    }

    public void deleteReservation(Long id) {
        // id 유지를 위해 리스트에서 제거된 객체는 모든 요소를 null로 유지
        // DB가 적용되면서 바뀔 예정
        validateDeleteReservation(id);
        reservations.set(id.intValue() - 1, new Reservation(null, null, null, null));
    }

    private void validateDeleteReservation(Long id) {
        if (reservations.size() < id || reservations.get(id.intValue() - 1).id() == null) {
            throw new IllegalArgumentException("존재하지 않는 id입니다.");
        }
    }
}
