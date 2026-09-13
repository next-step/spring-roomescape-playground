package roomescape;

import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalTime;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<ReservationTime> findAll() {
        return timeRepository.findAll();
    }

    public ReservationTime create(LocalTime time) {
        ReservationTime reservationTime =
                new ReservationTime(null, time);

        return timeRepository.save(reservationTime.getTime());
    }

    public void delete(Long id) {
        int deletedCount = timeRepository.delete(id);

        if (deletedCount == 0) {
            throw new NotFoundTimeException();
        }
    }
}