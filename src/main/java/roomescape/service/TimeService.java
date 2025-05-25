package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Time;
import roomescape.dao.TimeDAO;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeDAO timeDAO;
    private final ReservationDAO reservationDAO;

    public Time add(Time time) {
        return timeDAO.save(time);
    }

    public List<Time> findAll() {
        return timeDAO.findAll();
    }

    public void delete(Long id) {
        if (reservationDAO.findByTimeId(id)) {
            throw new IllegalStateException("해당 시간대는 예약이 존재하여 삭제할 수 없습니다.");
        }
        timeDAO.deleteById(id);
    }
}
