package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.repository.ReservationDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }
}
