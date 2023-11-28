package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDao {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);
}
