package roomescape;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    boolean deleteById(Long id);
}
