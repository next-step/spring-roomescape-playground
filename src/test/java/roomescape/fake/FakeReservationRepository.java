package roomescape.fake;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservedDateTime;
import roomescape.domain.time.Time;
import roomescape.repository.reservation.interfaces.ReservationRepository;

public class FakeReservationRepository implements ReservationRepository {

    private final Map<Long, Reservation> reservations;
    private final AtomicLong index;

    public FakeReservationRepository() {
        this.reservations = new HashMap<>();
        this.index = new AtomicLong(1);
    }


    @Override
    public Reservation save(Reservation reservation) {
        if (Objects.isNull(reservation.getId())) {
            reservation =
                    new Reservation(
                            index.getAndIncrement(),
                            reservation.getName(),
                            new ReservedDateTime(reservation.reservedDateValue(), reservation.getTime()));
        }
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream().toList();
    }

    @Override
    public void delete(Reservation reservation) {
        reservations.remove(reservation.getId());
    }

    @Override
    public boolean isExistsByReservedDateAndTime(LocalDate reservedDate, Time time) {
        Predicate<Reservation> dateMatch = reservation -> reservation.reservedDateValue().equals(reservedDate);
        Predicate<Reservation> timeMatch = reservation -> reservation.getTimeId().equals(time.getId());
        return reservations.values().stream().anyMatch(dateMatch.and(timeMatch));
    }
}
