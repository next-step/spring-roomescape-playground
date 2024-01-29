package roomescape.domain;

import java.util.List;

public interface Reservations {
    Reservation add(Reservation reservation);

    List<Reservation> add(Reservation... reservations);

    List<Reservation> getAll();

    void cancel(Long id);
}
