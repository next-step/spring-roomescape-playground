package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dao.TimeDAO;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeDAO timeDAO;

    public Time add(Time time) {
        return timeDAO.save(time);
    }

    public List<Time> findAll() {
        return timeDAO.findAll();
    }

    public void delete(Long id) {
        timeDAO.deleteById(id);
    }
}
