package roomescape.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }
}
