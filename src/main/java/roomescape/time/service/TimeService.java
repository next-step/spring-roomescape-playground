package roomescape.time.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import roomescape.time.domain.Time;
import roomescape.time.dto.request.TimeRequest;
import roomescape.time.dto.response.TimeResponse;
import roomescape.time.repository.TimeRepository;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TimeService {

    private final TimeRepository timeRepository;

    @Transactional
    public List<Time> findTimes() {
        return timeRepository.findAll();
    }

    @Transactional
    public TimeResponse.createTimeDto saveTime(TimeRequest.CreateTimeDto request) {
        if (request.time() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "양식을 채워주세요.");
        }
        return new TimeResponse.createTimeDto(createTime(request));
    }

    private Time createTime(TimeRequest.CreateTimeDto request) {
        Time time = request.toEntity();
        timeRepository.save(time);
        return time;
    }

    @Transactional
    public void removeTime(Long id) {
        Time time = timeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        timeRepository.delete(time);
    }
}
