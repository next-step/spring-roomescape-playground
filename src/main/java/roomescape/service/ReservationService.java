package roomescape.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.TimeDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.domain.Time;

import java.util.List;
import java.util.NoSuchElementException;

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
        Time time;

        try {
            time = timeDAO.findById(request.getTime());
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("해당 시간을 찾을 수 없습니다");
        }
        Long generatedId = reservationDAO.insertWithKeyHolder(request.getName(), request.getDate(), request.getTime());

        return Reservation.toEntity(request, generatedId, time);
    }

    public void delete(Long id) {
        int deletedCount = reservationDAO.delete(id);
        if (deletedCount == 0) {
            throw new NoSuchElementException("해당 예약을 찾을 수 없습니다");
        }
    }
}
