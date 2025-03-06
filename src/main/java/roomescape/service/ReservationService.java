package roomescape.service;


import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import roomescape.dao.reservation.ReservationJdbcDAO;
import roomescape.entity.Reservation;
import roomescape.exception.DataInvalidException;
import roomescape.exception.InvalidException;
import roomescape.exception.ReservationNotFoundException;

@Service
public class ReservationService {

    private final ReservationJdbcDAO reservationJdbcDAO;

    public ReservationService(ReservationJdbcDAO reservationJdbcDAO) {
        this.reservationJdbcDAO = reservationJdbcDAO;
    }

    public List<Reservation> getAllReservations() {
        try {
            return reservationJdbcDAO.getAll();
        } catch (DataAccessException e) {
            throw new DataInvalidException("예약 데이터를 불러오는데 실패했습니다.");
        }
    }

    public Reservation createReservation(Reservation reservation) {
        try {
            return reservationJdbcDAO.create(reservation);
        } catch (DataAccessException e) {
            throw new DataInvalidException("예약을 생성하는데 실패했습니다.");
        }
    }

    public Reservation getReservationById(long id) {
        Reservation reservation = reservationJdbcDAO.getById(id);
        if (reservation == null) {
            throw new ReservationNotFoundException("예약을 찾을 수 없습니다." + id);
        }
        return reservation;
    }

    public void deleteReservationById(long id) {
        Reservation reservation = reservationJdbcDAO.getById(id);
        if (reservation == null) {
            throw new InvalidException("예약을 찾을 수 없습니다." + id);
        }
        reservationJdbcDAO.delete(id);
    }

}
