package roomescape.service;


import org.springframework.stereotype.Service;
import roomescape.dto.TimeDTO;
import roomescape.entity.Time;
import roomescape.repository.TimeRepo;

import java.util.List;

@Service
public class TimeService {

    private TimeRepo timeRepo;

    public TimeService(TimeRepo timeRepo) {
        this.timeRepo = timeRepo;
    }


    public int create(TimeDTO timeDTO) {
        return timeRepo.insert(timeDTO);
    }


    public List<Time> findAll() {
        return timeRepo.findAll();
    }

    public void delete(Long id) {
        timeRepo.delete(id);
    }
}
