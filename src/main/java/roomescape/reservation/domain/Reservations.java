package roomescape.reservation.domain;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;
import roomescape.reservation.dao.ReservationsDao;

import java.util.List;

@Component
public class Reservations {
    private final ReservationsDao reservationsDao;

    public Reservations(ReservationsDao reservationsDao) {
        this.reservationsDao = reservationsDao;
    }

    public @Nonnull List<Reservation> getAll() {
        return reservationsDao.getAll();
    }

    public @Nonnull Reservation create(@Nonnull CreateReservationInfo info) {
        Reservation previous = reservationsDao.getByTime(info.time());
        if (previous != null) {
            throw new ReservationException.DuplicateTime();
        }

        return reservationsDao.create(info);
    }

    public void delete(@Nonnull ReservationId id) {
        reservationsDao.delete(id);
    }
}
