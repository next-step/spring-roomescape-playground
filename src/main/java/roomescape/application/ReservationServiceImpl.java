package roomescape.application;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReservationDao reservationDao;
    private final ReservationTimeDao timeDao;


    public ReservationServiceImpl(final ReservationDao reservationDao,
                                  final ReservationTimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    @Override
    public Reservation save(final ReservationRequest request) {
        final ReservationTime findTime = timeDao.findById(request.timeId());
        final Reservation reservation = new Reservation(request.name(), request.date(), findTime);
        return reservationDao.save(reservation);
    }

    @Override
    public void deleteById(final Long id) {
        reservationDao.deleteById(id);
    }
}
