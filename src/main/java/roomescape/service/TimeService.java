package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.exception.DuplicateReservationTimeException;
import roomescape.exception.NotFoundTimeException;
import roomescape.exception.TimeInUseException;
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

    public List<ReservationTime> findAll() {
        return timeRepository.findAll();
    }

    @Transactional
    public ReservationTime create(LocalTime time) {
        ReservationTime reservationTime = ReservationTime.create(time);
        if (timeRepository.existsByTime(time)) {
            throw new DuplicateReservationTimeException();
        }
        try {
            return timeRepository.save(reservationTime);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateReservationTimeException();
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (reservationRepository.existsByTimeId(id)) {
            throw new TimeInUseException(id);
        }
        try {
            if (!timeRepository.deleteById(id)) {
                throw new NotFoundTimeException(id);
            }
        } catch (DataIntegrityViolationException exception) {
            throw new TimeInUseException(id);
        }
    }
}
