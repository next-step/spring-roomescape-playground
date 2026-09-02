package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.TimeDAO;
import roomescape.domain.time.Time;
import roomescape.domain.time.TimeRequest;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TimeService {
    private final TimeDAO timeDAO;

    public TimeService (TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public List<Time> read() {
        return timeDAO.findAllTimes();
    }

    public Time create(TimeRequest request) {
        Long generatedId = timeDAO.insertWithKeyHolder(request);

        return Time.toEntity(request, generatedId);
    }

    public void delete(Long id) {
        int deletedCount = timeDAO.delete(id);
        if (deletedCount == 0) {
            throw new NoSuchElementException("해당 시간을 찾을 수 없습니다");
        }
    }
}
