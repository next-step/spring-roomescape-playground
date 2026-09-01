package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<ReservationTime> findAll() {
        return timeRepository.findAll();
    }

    public ReservationTime create(ReservationTime reservationTime) {
        return timeRepository.save(reservationTime);
    }

    public void deleteById(Long id) {
        if (!timeRepository.deleteById(id)) {
            throw new NotFoundTimeException(id);
        }
    }
}
