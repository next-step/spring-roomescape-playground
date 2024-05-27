package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.DAO.ReservationDAO;
import roomescape.DAO.TimeDAO;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;
    private final TimeDAO timeDAO;

    @Autowired
    public ReservationService(ReservationDAO reservationDAO, TimeDAO timeDAO) {
        this.reservationDAO = reservationDAO;
        this.timeDAO = timeDAO;
    }

    public List<ReservationResponse> findAll() {
        return reservationDAO.findAll();
    }

    public ReservationResponse findById(long id) {
        return reservationDAO.findById(id);
    }

    public long save(ReservationRequest request) {
        Time time = timeDAO.findById(request.timeId());
        Reservation reservation = new Reservation(null, request.name(), request.date(), time);
        return reservationDAO.save(reservation);
    }

    public long deleteById(long id) {
        return reservationDAO.deleteById(id);
    }
}