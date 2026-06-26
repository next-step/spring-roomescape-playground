package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {

    private final TimeRepository timeRepository;
    private final ReservationRepository reservationRepository;

    public TimeService(TimeRepository timeRepository, ReservationRepository reservationRepository) {
        this.timeRepository = timeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<TimeResponse> getAllTime() {
        return timeRepository.findAll().stream()
                .map(TimeResponse::from)
                .toList();
    }

    public TimeResponse createTime(TimeRequest timeRequest) {
        Time time = Time.from(timeRequest.time());
        if (timeRepository.existsByTime(timeRequest.time())) {
            throw new RoomEscapeException(ErrorCode.DUPLICATE_TIME);
        }
        return TimeResponse.from(timeRepository.insert(time));
    }

    public void deleteTime(Long id) {
        Time time = timeRepository.findById(id)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.TIME_NOT_FOUND));
        if (reservationRepository.existsByTimeId(id)) {
            throw new RoomEscapeException(ErrorCode.TIME_IN_USE);
        }

        timeRepository.delete(time);
    }
}
