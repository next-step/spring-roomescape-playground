package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roomescape.controller.dto.ReservationCreate;
import roomescape.controller.dto.ReservationResponse;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationServiceImpl(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        return reservationDao.findAll().stream()
            .map(ReservationResponse::from)
            .toList();
    }

    @Override
    public ReservationResponse add(ReservationCreate request) throws NoSuchElementException {
        Time time = timeDao.findById(request.time());

        if (time == null) {
            throw new NoSuchElementException("존재하지 않는 시간대입니다.");
        }

        Reservation reservation = new Reservation(null, request.name(), request.date(), time);
        return ReservationResponse.from(reservationDao.save(reservation));
    }

    @Override
    public void remove(Long id) throws NoSuchElementException {
        if (!reservationDao.existsById(id)) {
            throw new NoSuchElementException("존재하지 않는 예약입니다.");
        }

        reservationDao.deleteById(id);
    }
}
