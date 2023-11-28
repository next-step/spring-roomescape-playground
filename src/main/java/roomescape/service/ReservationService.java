package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.dto.CreateReservation;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    @Transactional
    public Reservation create(CreateReservation request) {
        Reservation reservation = request.toReservation();
        return reservationDao.save(reservation);
    }

    @Transactional
    public void remove(Long id) {
        if(!reservationDao.existsById(id)) {
            throw new IllegalArgumentException("예약이 존재하지 않습니다.");
        }
        reservationDao.deleteById(id);
    }
}
