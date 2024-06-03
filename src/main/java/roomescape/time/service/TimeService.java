package roomescape.time.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.time.db.Time;
import roomescape.time.db.TimeRepositoryImpl;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeRepositoryImpl timeRepository;

    public List<TimeResponse> findAlltime() {
        List<Time> times = timeRepository.findAll();
        List<TimeResponse> timeResponses = times.stream()
                .map(TimeResponse::from)
                .toList();
        return timeResponses;
    }

    public TimeResponse addTime(TimeRequest timeRequest) {

        Time time = Time.builder()
                .time(timeRequest.getTime())
                .build();

        Time savedTime = timeRepository.save(time);
        return TimeResponse.from(savedTime);
    }

    public void deleteTime(Long id) {
        Time time = timeRepository.findById(id);
        if (time == null) {
            throw new RuntimeException("찾을 수 없는 id입니다");
        }
        timeRepository.deleteById(time.getId());
    }

    public TimeResponse findById(Long id) {
        Time time = timeRepository.findById(id);
        if (time == null) {
            throw new RuntimeException("찾을 수 없는 id입니다");
        }
        return TimeResponse.from(time);
    }
}




