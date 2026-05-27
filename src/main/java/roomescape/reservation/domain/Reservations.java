package roomescape.reservation.domain;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;
import roomescape.reservation.repository.ReservationsRepository;
import roomescape.time.domain.Times;

import java.util.List;

@Component
public class Reservations {
    private final ReservationsRepository reservationsRepository;
    private final Times times;

    public Reservations(ReservationsRepository reservationsRepository, Times times) {
        this.reservationsRepository = reservationsRepository;
        this.times = times;
    }

    public @Nonnull List<Reservation> getAll() {
        return reservationsRepository.getAll();
    }

    public @Nonnull Reservation create(@Nonnull CreateReservationInfo info) {
        if(!times.has(info.timeId())) {
            throw new ReservationException.TimeNotFound();
        }

        Reservation previous = reservationsRepository.getByDateTime(info.date(), info.timeId());
        if (previous != null) {
            throw new ReservationException.DuplicateDateTime(previous.getId());
        }

        return reservationsRepository.create(info);
    }

    public void delete(@Nonnull ReservationId id) {
        reservationsRepository.delete(id);
    }
}
