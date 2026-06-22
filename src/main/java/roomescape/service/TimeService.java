package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeRequest;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<ReservationTime> getAllTimes() {
        return timeRepository.findAll();
    }

    public ReservationTime createTime(TimeRequest request) {
        ReservationTime newTime = new ReservationTime(null, request.time());
        return timeRepository.save(newTime);
    }

    public void deleteTime(Long id) {
        if (!timeRepository.deleteById(id)) {
            throw new IllegalArgumentException("존재하지 않는 시간입니다.");
        }
    }
}
