package roomescape.repository;

import java.util.List;
import roomescape.model.Reservation;
import roomescape.model.Time;

public interface ReservationRepository {

    boolean existById(Long id);

    Reservation findById(Long id);

    List<Reservation> findAll();

    Time findTimeById(Long timeId);

    Reservation save(Reservation reservation);

    void delete(Long id);

}
