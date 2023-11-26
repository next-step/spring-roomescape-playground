package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import roomescape.dto.Reservation;

public class ReservationService {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public List<Reservation> getAllReservations() {
        return reservations.stream()
            .filter(reservation -> reservation.id() != null)
            .toList();
    }

    public Reservation addReservation(Reservation reservation) {
        reservations.add(new Reservation(index.get(), reservation.name(), reservation.date(), reservation.time()));
        return reservations.get((int)index.getAndIncrement() - 1);
    }

    public void deleteReservation(Long id) {
        // id 유지를 위해 리스트에서 제거된 객체는 모든 요소를 null로 유지
        // DB가 적용되면서 바뀔 예정
        reservations.set(id.intValue() - 1, new Reservation(null, null, null, null));
    }
}
