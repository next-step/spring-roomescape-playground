package roomescape.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryReservations implements Reservations {

    private final AtomicLong ids;
    private final List<Reservation> reservations;

    public InMemoryReservations() {
        this.ids = new AtomicLong();
        this.reservations = new ArrayList<>();
    }

    @Override
    public Reservation add(Reservation reservation) {
        Reservation saved = reservation.with(ids.incrementAndGet());
        reservations.add(saved);
        return saved;
    }

    @Override
    public List<Reservation> add(Reservation... reservations) {
        List<Reservation> saved = new ArrayList<>();
        for (Reservation reservation : reservations) {
            saved.add(add(reservation));
        }
        return saved;
    }

    @Override
    public List<Reservation> getAll() {
        return new ArrayList<>(reservations);
    }

    @Override
    public void cancel(Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("id가 " + id + "인 예약을 찾을 수 없습니다"));
        reservations.remove(reservation);
    }
}
