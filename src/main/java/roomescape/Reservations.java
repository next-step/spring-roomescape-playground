package roomescape;

import java.util.ArrayList;
import java.util.List;

public class Reservations {

    List<Reservation> reservations = new ArrayList<>();

    private AutoIncrementId autoIncrementId = new AutoIncrementId();


    public void register(final Reservation reservation) {
        reservation.setId(autoIncrementId.getNextId());
        reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void cancel(final Long id) {
        Reservation foundReservation = reservations.stream().filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException(id));
        reservations.remove(foundReservation);
    }

}
