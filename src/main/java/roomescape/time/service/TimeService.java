package roomescape.time.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.time.db.TimeEntity;
import roomescape.time.db.TimeRepository;
import roomescape.time.model.TimeRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeRepository timeRepository;

    public List<TimeEntity> findAllList() {
        return timeRepository.findAllList();
    }

    public int countTime() {
        return timeRepository.countTime();
    }

    public TimeEntity findTimeById(Long id) {
        return timeRepository.findTimeById(id);
    }

    public TimeEntity insertTime(TimeRequest timeRequest) {
        return timeRepository.insertTime(timeRequest);
    }

    public void deleteTime(Long id) {
        timeRepository.deleteTime(id);
    }
}
