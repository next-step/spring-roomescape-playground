package roomescape.service_layer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.repository_layer.dao.TimeDao;
import roomescape.web_layer.controller.exception.FailMessage;
import roomescape.web_layer.controller.exception.NotFoundTimeException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeDao timeDao;

    public TimeResponse registerTime(TimeRequest request) {
        Time time = Time.of(null, request.time());
        Long id = timeDao.insert(time);
        return TimeResponse.from(Time.of(id, request.time()));
    }

    public List<TimeResponse> getTime() {
        return timeDao.findAll().stream().map(TimeResponse::from).toList();
    }

    public TimeResponse getTimeById(Long id) {
        try {
            return TimeResponse.from(timeDao.findById(id));
        } catch (DataAccessException e) {
            throw new NotFoundTimeException(FailMessage.BAD_REQUEST);
        }
    }

    public void deleteTime(Long id) {
        int result = timeDao.delete(id);
        if (result == 0) {
            throw new NotFoundTimeException(FailMessage.BAD_REQUEST);
        }
    }
}
