package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong; // 1. ID 생성을 위해 추가

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();

    private final AtomicLong counter = new AtomicLong();

    public ReservationService() {
        reservations.add(new Reservation(counter.incrementAndGet(), "브라운", "2025-01-01", "10:00"));
        reservations.add(new Reservation(counter.incrementAndGet(), "코니", "2025-01-02", "11:00"));
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public Reservation addReservation(Reservation newReservation) {
        Reservation savedReservation = new Reservation(
                counter.incrementAndGet(), // 새 ID 발급
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime()
        );
        reservations.add(savedReservation);
        return savedReservation;
    }



    public void deleteReservation(Long id) {
        boolean exists = reservations.stream()
                .anyMatch(reservation -> reservation.getId().equals(id));

        if (!exists) {
            throw new IllegalArgumentException("존재하지 않는 예약 ID입니다: " + id);
        }

        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    public void clear() {
        reservations.clear();
        counter.set(0L);      //테스트를 위한 코드 입니다.
    }
}
