package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.exception.NotFoundDataException;
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

    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    public Time save(TimeRequest request) {
        Time newTime = new Time(null, request.time());
        return timeRepository.save(newTime);
    }

    public void deleteById(Long id) {
        boolean deleted = timeRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundDataException("존재하지 않는 시간입니다.");
        }
    }
}
