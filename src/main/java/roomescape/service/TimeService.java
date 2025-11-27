package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.exception.InvalidTimeRequestException;
import roomescape.exception.NotFoundTimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TimeService {

    private final TimeDao timeDao;
    private static final Logger logger = Logger.getLogger(TimeService.class.getName());

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<TimeResponse> findAll() {
        List<Time> results = timeDao.findAll();
        List<TimeResponse> responses = new ArrayList<>();
        for (Time t : results) {
            responses.add(new TimeResponse(t.getId(), t.getTime()));
        }
        return responses;
    }

    public TimeResponse create(TimeRequest request) {
        if (request.time() == null || request.time().isBlank()) {
            logger.warning("Invalid time request: " + request);
            throw new InvalidTimeRequestException("잘못된 시간 요청입니다.");
        }
        long id = timeDao.insert(request.time());
        Time saved = timeDao.findById(id);
        return new TimeResponse(saved.getId(), saved.getTime());
    }

    public void delete(long id) {
        int updated = timeDao.deleteById(id);
        if (updated == 0) {
            throw new NotFoundTimeException("해당 시간을 찾을 수 없습니다: " + id);
        }
    }
}


