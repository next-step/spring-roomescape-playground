package roomescape.time.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;
import roomescape.time.exception.BadRequestTimeException;
import roomescape.time.exception.TimeNotFoundException;
import roomescape.time.repository.TimeRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeService {

    public final TimeRepositoryImpl timeRepository;

    public List<TimeResponse> findAllTime() {
        List<Time> times = timeRepository.findAll();
        List<TimeResponse> timeResponses = times.stream()
                .map(TimeResponse::from)
                .collect(Collectors.toList());

        return timeResponses;
    }

    public TimeResponse addTime(TimeRequest timeRequest) {
        Time time = new Time(timeRequest.getTime());
        Time savedTime = timeRepository.save(time);
        return TimeResponse.from(savedTime);
    }

    public void deleteTime(Long id) {
        Time time = timeRepository.findById(id);
        if (time == null ){
            throw new TimeNotFoundException("찾을 수 없는 time id 입니다.");
        }
        timeRepository.deleteById(time.getId());
    }

    public TimeResponse findById(Long id) {
        Time time = timeRepository.findById(id);
        if (time == null ){
            throw new TimeNotFoundException("찾을 수 없는 time id 입니다.");
        }
        return TimeResponse.from(time);
    }

}
