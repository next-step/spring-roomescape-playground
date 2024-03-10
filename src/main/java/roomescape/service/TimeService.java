package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.repository.dao.TimeDao;
import roomescape.repository.domain.Time;
import roomescape.dto.TimeDTO;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TimeService {

    private final TimeDao timeDao;

    @Autowired
    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<TimeDTO> getAllTimes() {
        List<Time> times = timeDao.getAllTimes();
        return times.stream()
                .map(this::convertToTimeDTO)
                .collect(Collectors.toList());
    }

    public TimeDTO insertTime(Time time) {
        Time newTime = timeDao.insertTime(time);
        return convertToTimeDTO(newTime);
    }

    public void deleteTime(Long id) {
        int removed = timeDao.deleteTime(id);
        if (removed == 0) {
            throw new NoSuchElementException("삭제할 항목이 없습니다.");
        }
    }

    private TimeDTO convertToTimeDTO(Time time) {
        return new TimeDTO(time.getId(), time.getTime());
    }
}

