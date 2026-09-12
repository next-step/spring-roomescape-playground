package roomescape.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.exception.DuplicateTimeException;
import roomescape.exception.TimeInUseException;
import roomescape.exception.TimeNotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.LocalTime;
import java.util.List;

@Service
public class TimeService {

    private final TimeRepository timeRepository;
    private final ReservationRepository reservationRepository;

    public TimeService(TimeRepository timeRepository, ReservationRepository reservationRepository) {
        this.timeRepository = timeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    public Time create(LocalTime requestTime) {
        Time time = new Time(requestTime);

        try {
            return timeRepository.save(time);
        } catch (DuplicateKeyException e) {
            throw new DuplicateTimeException("이미 존재하는 시간입니다.");
        }
    }

    public void delete(Long id) {
        if (reservationRepository.existsByTimeId(id)) {
            throw new TimeInUseException("이미 사용 중인 시간을 삭제할 수 없습니다.");
        }
        if (!timeRepository.deleteById(id)) {
            throw new TimeNotFoundException("존재하지 않는 시간입니다.");
        }
    }
}
