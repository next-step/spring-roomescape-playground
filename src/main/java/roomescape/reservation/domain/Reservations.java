package roomescape.reservation.domain;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;
import roomescape.reservation.repository.ReservationsRepository;

import java.util.List;

@Component
public class Reservations {
    private final ReservationsRepository reservationsRepository;

    public Reservations(ReservationsRepository reservationsRepository) {
        this.reservationsRepository = reservationsRepository;
    }

    public @Nonnull List<Reservation> getAll() {
        return reservationsRepository.getAll();
    }

    public @Nonnull Reservation create(@Nonnull CreateReservationInfo info) {
        Reservation previous = reservationsRepository.getByTime(info.time());
        if (previous != null) {
            throw new ReservationException.DuplicateTime();
        }

        return reservationsRepository.create(info);
    }

    public void delete(@Nonnull ReservationId id) {
        reservationsRepository.delete(id);
    }
}
