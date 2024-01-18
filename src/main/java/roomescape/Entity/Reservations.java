package roomescape.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Reservations {
    private final List<Reservation> reservations = new ArrayList<>();

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public Optional<Reservation> findById(long id) {
        return reservations.stream()
                .filter(reservation -> reservation.isEqualId(id))
                .findFirst();
    }

    public void remove(Reservation reservation) {
        reservations.remove(reservation);
    }

    public List<Reservation> getAll() {
        return new ArrayList<>(reservations);
    }
}
