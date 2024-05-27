package roomescape.time.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.time.db.TimeEntity;
import roomescape.time.db.TimeRepository;
import roomescape.time.db.TimeRepositoryImpl;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;

import java.sql.Time;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeRepositoryImpl timeRepository;

    public List<TimeResponse> findAlltime() {
        List<TimeEntity> times = timeRepository.findAll();
        List<TimeResponse> timeResponses = times.stream()
                .map(TimeResponse::from)
                .toList();
        return timeResponses;
    }

    public TimeResponse addTime(TimeRequest timeRequest) {

        TimeEntity timeEntity = TimeEntity.builder()
                .time(timeRequest.getTime())
                .build();

        TimeEntity savedTime = timeRepository.save(timeEntity);
        return TimeResponse.from(savedTime);
    }

    public void deleteTime(Long id) {
        TimeEntity timeEntity = timeRepository.findById(id);
        if (timeEntity == null) {
            throw new RuntimeException("찾을 수 없는 id입니다");
        }
        timeRepository.deleteById(timeEntity.getId());
    }

    public TimeResponse findById(Long id) {
        TimeEntity timeEntity = timeRepository.findById(id);
        if (timeEntity == null) {
            throw new RuntimeException("찾을 수 없는 id입니다");
        }
        return TimeResponse.from(timeEntity);
    }
}




