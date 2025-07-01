package roomescape.reservation.service;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.model.Reservation;
import roomescape.time.model.Time;

@Service
public class ReservationManager {

    private final ReservationDao reservationDao;

    public ReservationManager(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation create(final Time time, final String name, final LocalDate date) {
        return reservationDao.insert(
            time,
            name,
            date
        );
    }
}
