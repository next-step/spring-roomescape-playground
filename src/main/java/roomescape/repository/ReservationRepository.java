package roomescape.repository; // (새 패키지)

import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.dto.ReservationCreateRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public ReservationRepository() {
        this.save(new Reservation(null, "브라운", "2025-01-01", "12:0"));
        this.save(new Reservation(null, "코니", "2025-01-02", "11:00"));
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    public Reservation save(Reservation reservation) {
        Reservation savedReservation = new Reservation(
                counter.incrementAndGet(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
        reservations.add(savedReservation);
        return savedReservation;
    }

    public boolean existsById(Long id) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.getId().equals(id));
    }

    public void deleteById(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    public void clear() {
        reservations.clear();
        counter.set(0L);
    }}
