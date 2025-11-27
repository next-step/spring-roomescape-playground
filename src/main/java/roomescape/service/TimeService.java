package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.exception.FailMessage;
import roomescape.exception.NotFoundTimeException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeDao timeDao;

    public Time registerTime(String time) {
        Time times = Time.createTime(null, time);
        Long id = timeDao.insert(times);
        return Time.createTime(id, time);
    }

    public List<Time> getTime() {
        return timeDao.findAll();
    }

    public Time getTimeById(Long id) {
        try {
            return timeDao.findById(id);
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
