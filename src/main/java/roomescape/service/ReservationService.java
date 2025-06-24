package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.RequestReservation;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.global.exception.NotFoundException;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation create(final RequestReservation requestReservation) {
        requestReservation.validatePasted();
        return reservationDao.save(requestReservation);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation findById(final Long id) {
        return reservationDao.findById(id)
                .orElseThrow(() -> new NotFoundException("예약 ID가 존재하지 않아요."));
    }

    public void delete(final Long id) {
        Reservation reservation = findById(id);
        reservationDao.deleteById(reservation.id());
    }
}
