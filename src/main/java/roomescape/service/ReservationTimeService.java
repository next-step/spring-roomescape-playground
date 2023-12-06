package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.dto.CreateReservationTime;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@Service
@Transactional(readOnly = true)
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeDao.findAll().stream()
            .map(ReservationTimeResponse::from)
            .toList();
    }

    @Transactional
    public ReservationTime create(CreateReservationTime request) {
        ReservationTime reservationTime = request.toReservationTime();
        return reservationTimeDao.save(reservationTime);
    }

    @Transactional
    public void remove(Long id) {
        if (!reservationTimeDao.existsById(id)) {
            throw new IllegalArgumentException("예약 시간이 존재하지 않습니다. id: " + id);
        }
        reservationTimeDao.deleteById(id);
    }
}
