package roomescape.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();


    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

}
