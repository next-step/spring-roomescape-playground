package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.TimeDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.domain.Time;
import roomescape.exception.NotFoundReservationException;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;
    private final TimeDAO timeDAO;

    public ReservationService(ReservationDAO reservationDAO, TimeDAO timeDAO) {
        this.reservationDAO = reservationDAO;
        this.timeDAO = timeDAO;
    }

    public List<Reservation> read() {
        return reservationDAO.findAllReservations();
    }

    public Reservation create(ReservationRequest request) {
        Long generatedId = reservationDAO.insertWithKeyHolder(request);
        Time time = timeDAO.findById(request.getTime());

        return Reservation.toEntity(request, generatedId, time);
    }

    public void delete(Long id) {
        int deletedCount = reservationDAO.delete(id);
        if (deletedCount == 0) {
            throw new NotFoundReservationException("해당 예약을 찾을 수 없습니다");
        }
    }
}
