package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import roomescape.dto.TimeCreateRequest;
import roomescape.exception.DuplicateTimeException;
import roomescape.exception.TimeInUseException;
import roomescape.exception.TimeNotFoundException;
import roomescape.model.Time;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {
    private final TimeRepository timeRepository;
    private final ReservationRepository reservationRepository;

    public TimeService(TimeRepository timeRepository, ReservationRepository reservationRepository) {
        this.timeRepository = timeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Time> getTimes() {
        return timeRepository.findAll();
    }

    public Time getById(int id) {
        return timeRepository.findById(id).orElseThrow(() -> new TimeNotFoundException("올바르지 않은 시간 ID 입니다."));
    }

    public Time createTime(TimeCreateRequest request) {
        LocalTime parsedTime = LocalTime.parse(request.time());

        Time time = Time.create(parsedTime);

        try {
            return timeRepository.save(time);
        } catch (DuplicateKeyException e) {
            throw new DuplicateTimeException("이미 존재하는 시간입니다.");
        }
    }

    public void deleteTime(int id) {
        // 1. 시간 존재 확인
        timeRepository.findById(id).orElseThrow(() -> new TimeNotFoundException("시간이 존재하지 않습니다."));

        // 2. 시간이 예약에 사용중인지 확인
        boolean isInUse = reservationRepository.existsByTimeId(id);
        if (isInUse) throw new TimeInUseException("예약에 사용중인 시간은 삭제할 수 없습니다.");

        // 3. 삭제
        timeRepository.deleteById(id);
    }
}
