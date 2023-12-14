package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.dto.CreateReservation;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    @Transactional
    public Reservation create(CreateReservation request) {
        Time time = timeDao.findById(request.time());
        Reservation reservation = new Reservation(null, request.name(), request.date(), time);
        return reservationDao.save(reservation);
    }

    @Transactional
    public void remove(Long id) {
        if (!reservationDao.existsById(id)) {
            throw new IllegalArgumentException("예약이 존재하지 않습니다.");
        }
        reservationDao.deleteById(id);
    }
}
