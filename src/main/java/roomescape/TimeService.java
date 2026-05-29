package roomescape;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.exception.NotFoundTimeException;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public Time createTime(Time time) {
        Long generatedId = timeDao.insert(time);
        return Time.toEntity(time, generatedId);
    }

    public List<Time> findAllTimes() {
        return timeDao.findAll();
    }

    public void deleteTime(Long id) {
        int updatedRows = timeDao.deleteById(id);

        if (updatedRows == 0) {
            throw new NotFoundTimeException(id);
        }
    }
}
