package roomescape.time;

import java.util.Collection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import roomescape.exception.NotFoundException;
import roomescape.reservation.ReservationRepository;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeCreateRequest;
import roomescape.time.dto.TimeResponse;

@Service
public class TimeService {
    private final TimeRepository timeRepository;
    private final ReservationRepository reservationRepository;

    public TimeService(TimeRepository timeRepository, ReservationRepository reservationRepository) {
        this.timeRepository = timeRepository;
        this.reservationRepository = reservationRepository;
    }

    public Collection<TimeResponse> findAll() {
        return timeRepository.findAll()
                .stream()
                .map(TimeResponse::from)
                .toList();
    }

    public TimeResponse create(TimeCreateRequest request) {

        try {
            Time time = new Time(null, request.time());
            return TimeResponse.from(timeRepository.save(time));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 존재하는 시간입니다.");
        }
    }

    public void deleteById(Long id) {
        if (reservationRepository.existsByTime(id)) {
            throw new IllegalArgumentException("예약이 존재하는 시간은 삭제할 수 없습니다.");
        }
        int deleteCount = timeRepository.deleteById(id);

        if (deleteCount == 0) {
            throw new NotFoundException("존재하지 않는 id입니다.");
        }

    }
}
