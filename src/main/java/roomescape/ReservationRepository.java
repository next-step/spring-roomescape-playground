package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    boolean existsByDateAndTime(LocalDate date, LocalTime time);
}
