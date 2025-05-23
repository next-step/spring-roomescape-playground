package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDAO reservationDAO;
    private final TimeService timeService;

    public Reservation add(ReservationRequest request) {
        Time time = timeService.findById(request.getTimeId());
        Reservation reservation = request.toEntity(time);
        return reservationDAO.add(reservation);
    }

    public List<ReservationResponse> findAll() {
        return reservationDAO.findAll().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public void delete(int id) {
        reservationDAO.delete(id);
    }

    public Reservation update(Long id, ReservationRequest request) {
        Time time = timeService.findById(request.getTimeId());
        Reservation reservation = new Reservation(id.intValue(), request.getName(), request.getParsedDate(), time);
        reservationDAO.update(reservation);
        return reservation;
    }
}
