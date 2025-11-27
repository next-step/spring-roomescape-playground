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

@Service
public class TimeList {

    private final TimeDao timeDao;

    public TimeList(TimeDao timeDao) {
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


