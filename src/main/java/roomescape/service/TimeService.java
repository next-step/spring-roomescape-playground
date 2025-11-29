package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public TimeService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<TimeResponse> getAllTimes() {
        return timeRepository.findAll().stream()
                .map(TimeResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public TimeResponse createTime(TimeRequest request) {
        timeRepository.findByTime(request.time())
                .ifPresent(t -> { throw new IllegalStateException("이미 존재하는 시간입니다."); });

        Time time = Time.create(request.time());
        Time savedTime = timeRepository.save(time);
        return TimeResponse.from(savedTime);
    }

    @Transactional
    public void deleteTime(Long id) {
        if (reservationRepository.existsByTimeId(id)) {
            throw new IllegalStateException("예약이 존재하는 시간은 삭제할 수 없습니다.");
        }

        timeRepository.deleteById(id);
    }
}
