package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation registerReservation(String name, String date, String time) {
        Reservation reservation = Reservation.createReservation(null, name, date, time);
        Long id = reservationDao.insertWhithKeyHolder(reservation);
        return Reservation.newReservationFromDb(id, name, date, time);
    }

    public List<Reservation> getReservations() {
        return reservationDao.findAllReservation();
    }


    public void delete(Long id) {
        int result = reservationDao.delete(id);

        if (result == 0) {
            throw new NotFoundReservationException("삭제할 예약이 없습니다.");
        }
    }
}
