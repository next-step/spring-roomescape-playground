package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.exception.NotFoundReservationException;
import roomescape.model.Reservation;
import roomescape.model.ReservationRequest;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> getReservations() {
        return reservationDao.findAll();
    }

    public Reservation addReservation(ReservationRequest request) {
        request.validateAndSetTimeId(reservationDao.getJdbcTemplate());
        return reservationDao.save(request);
    }

    public void deleteReservation(Long id) {
        int rowsAffected = reservationDao.deleteById(id);
        if (rowsAffected == 0) {
            throw new NotFoundReservationException("삭제할 예약이 없습니다.");
        }
    }
}
