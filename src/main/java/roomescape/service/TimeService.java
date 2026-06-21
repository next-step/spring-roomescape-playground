package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    public Time create(TimeRequest timeRequest) {
        long id = timeRepository.insert(timeRequest);
        return new Time(
                id,
                timeRequest.time()
        );
    }

    public void delete(Long id) {
        boolean isRemoved = timeRepository.delete(id);

        if (!isRemoved) {
            throw new NotFoundReservationException();
        }
    }
}
