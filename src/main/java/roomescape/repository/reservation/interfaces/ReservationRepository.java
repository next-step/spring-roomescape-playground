package roomescape.repository.reservation.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.Time;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    List<Reservation> findAll();

    void delete(Reservation reservation);

    boolean isExistsByReservedDateAndTime(LocalDate reservedDate, Time time);
}
