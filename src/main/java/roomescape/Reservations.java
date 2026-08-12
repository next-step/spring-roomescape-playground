package roomescape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.exception.NotFoundReservationException;

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
        Reservation.validate(reservation);
        Reservation newReservation = Reservation.toEntity(index.getAndIncrement(), reservation);
        reservations.add(newReservation);
        return newReservation;
    }

    public void delete(Long id){
        Reservation deleteReservation = reservations.stream().filter(it-> Objects.equals(it.getId(),id)).findFirst().orElseThrow(() -> new NotFoundReservationException("해당 id의 예약이 존재하지 않습니다."));
        reservations.remove(deleteReservation);
    }
}
