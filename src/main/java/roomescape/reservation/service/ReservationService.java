package roomescape.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.dao.ReservationQueryRepository;
import roomescape.reservation.dao.ReservationUpdateRepository;
import roomescape.reservation.domain.Reservation;
import roomescape.time.dao.TimeQueryRepository;
import roomescape.time.domain.Time;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationQueryRepository queryRepository;
    private final ReservationUpdateRepository updateRepository;

    private final TimeQueryRepository timeQueryRepository;

    @Transactional
    public List<Reservation> getAllReservations() {
        return queryRepository.getAllReservations();
    }

    @Transactional
    public Reservation createReservation(String name, LocalDate date, Long time_id) {
        if (date == null || name == null || time_id == null ) {
            throw new NotFoundReservationException("빈 값이 존재합니다!");
        }
        Time time = timeQueryRepository.findById(time_id);
        return updateRepository.insert(name, date, time);
    }

    @Transactional
    public void deleteReservation(Long id) {
        updateRepository.delete(id);
    }

    public static class NotFoundReservationException extends RuntimeException {
        public NotFoundReservationException(String message) {
            super(message);
        }
    }
}
