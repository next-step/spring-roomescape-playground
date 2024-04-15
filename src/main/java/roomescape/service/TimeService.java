package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dto.TimeDTO;
import roomescape.entity.Time;
import roomescape.repository.TimeDAO;

import java.util.List;

@Service
public class TimeService {

    @Autowired
    private TimeDAO timeDAO;

    public List<Time> findAllTimes() {
        return timeDAO.findAll();
    }

    public Time saveTime(TimeDTO timeDTO) {
        Time time = TimeDTO.toEntity(timeDTO, null);
        Long id = timeDAO.insertWithKeyHolder(time);
        time.setId(id);

        return time;
    }

    public void deleteTime(Long id) {
        timeDAO.delete(id);
    }


}
