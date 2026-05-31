package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.dto.request.TimeCreateRequest;
import roomescape.dto.response.TimeCreateResponse;
import roomescape.dto.response.TimeGetResponse;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.NotFoundTimeException;

import java.util.List;

@Service
public class TimeService {

    private final TimeDao repository;

    public TimeService(TimeDao repository) {
        this.repository = repository;
    }

    public List<TimeGetResponse> getTimes() {
        List<Time> times;
        List<TimeGetResponse> response;

        times = repository.findAll();

        response = times.stream()
                .map(it -> new TimeGetResponse(
                        it.getId(),
                        it.getTime()))
                .toList();

        return response;
    }

    public TimeCreateResponse addTime(TimeCreateRequest request) {
        boolean exists = repository.existsByTime(request.getTime());
        if (exists) {
            throw new DuplicateReservationException("이미 예약된 시간입니다.");
        }

        Time newTime = new Time(
                null,
                request.getTime());

        newTime = repository.save(newTime);

        return new TimeCreateResponse(
                newTime.getId(),
                newTime.getTime());
    }

    public void deleteTime(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundTimeException();
        }
    }
}
