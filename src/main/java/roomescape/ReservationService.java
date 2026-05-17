package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.NotFoundReservationException;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation createReservation(Reservation reservation) {
        LocalDate date = reservation.getDate();
        LocalTime time = reservation.getTime();

        if (reservationDao.existsByDateAndTime(date, time)) {
            throw new DuplicateReservationException(date.toString(), time.toString());
        }

        Long generatedId = reservationDao.insert(reservation);
        return Reservation.toEntity(reservation, generatedId);
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAll();
    }

    public void deleteReservation(Long id) {
        int updatedRows = reservationDao.deleteById(id);

        if (updatedRows == 0) {
            throw new NotFoundReservationException(id);
        }
    }
}
