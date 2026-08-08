package roomescape;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();
}
