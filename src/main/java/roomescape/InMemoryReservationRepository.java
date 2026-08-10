package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    public InMemoryReservationRepository() {
        reservations.add(new Reservation(1L, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)));
        reservations.add(new Reservation(2L, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0)));
        reservations.add(new Reservation(3L, "브라운", LocalDate.of(2023, 1, 3), LocalTime.of(12, 0)));
    }

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }
}
