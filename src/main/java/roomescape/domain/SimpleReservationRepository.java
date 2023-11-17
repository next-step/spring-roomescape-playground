package roomescape.domain;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class SimpleReservationRepository {

    public List<Reservation> findAll() {
        return List.of(
                new Reservation(1L, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)),
                new Reservation(2L, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0))
        );
    }
}
