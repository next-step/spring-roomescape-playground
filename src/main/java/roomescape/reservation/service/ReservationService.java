package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestException;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.time.dao.TimeDao;
import roomescape.time.domain.Time;

import java.util.List;

@Service
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

    public Reservation save(ReservationRequest request) {
        Time time = timeDao.findById(request.time())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 시간입니다."));
        Reservation reservation = Reservation.of(request.name(), request.date(), time);
        return reservationDao.save(reservation);
    }

    public void deleteById(Long id) {
        reservationDao.deleteById(id);
    }
}
