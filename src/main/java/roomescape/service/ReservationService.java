package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.FailMessage;
import roomescape.exception.NotFoundReservationException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeService timeService;

    public Reservation registerReservation(String name, String date, String stringTime) {
        Long timeId = Long.parseLong(stringTime);
        Time time = timeService.getTimeById(timeId);
        Reservation reservation = Reservation.createReservation(null, name, date, time);
        Long id = reservationDao.insert(reservation);
        return Reservation.newReservationFromDb(id, name, date, time);
    }

    public List<Reservation> getReservations() {
        return reservationDao.findAll();
    }


    public void delete(Long id) {
        int result = reservationDao.delete(id);

        if (result == 0) {
            throw new NotFoundReservationException(FailMessage.BAD_REQUEST);
        }
    }
}
