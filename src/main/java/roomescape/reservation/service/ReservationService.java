package roomescape.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.dao.ReservationQueryRepository;
import roomescape.reservation.dao.ReservationUpdateRepository;
import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationQueryRepository queryRepository;
    private final ReservationUpdateRepository updateRepository;

    @Transactional
    public List<Reservation> getAllReservations() {
        return queryRepository.getAllReservations();
    }

    @Transactional
    public Reservation createReservation(String name, LocalDate date, LocalTime time) {
        if (date == null || name == null || time == null ) {
            throw new NotFoundReservationException("빈 값이 존재합니다!");
        }
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
