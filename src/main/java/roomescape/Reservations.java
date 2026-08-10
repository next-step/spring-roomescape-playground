package roomescape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class Reservations {
    private final List<Reservation> reservations;
    private final AtomicLong index = new AtomicLong(1);

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public List<Reservation> readReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public Reservation reserve(Reservation reservation){
        Reservation newReservation = Reservation.toEntity(index.getAndIncrement(), reservation);
        reservations.add(newReservation);
        return newReservation;
    }
}
