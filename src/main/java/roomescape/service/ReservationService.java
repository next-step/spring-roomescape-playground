package roomescape.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.ReservationNotFoundException;
import roomescape.exception.TimeNotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;
    private final Clock clock;

    ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository, Clock clock) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
        this.clock = clock;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation create(String name, LocalDate date, Long timeId) {
        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new TimeNotFoundException("존재하지 않는 시간입니다."));

        validateReservationDateTime(date, time.getTime());
        validateDuplicateReservation(name, date, timeId);

        Reservation reservation = new Reservation(name, date, time);

        try {
            return reservationRepository.save(reservation);
        } catch (DuplicateKeyException e) {
            throw new DuplicateReservationException("이미 존재하는 예약입니다.");
        }
    }

    public void delete(Long id) {
        if (!reservationRepository.deleteById(id)) {
            throw new ReservationNotFoundException("존재하지 않는 예약입니다.");
        }
    }

    private void validateReservationDateTime(LocalDate date, LocalTime time) {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time).truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime now = LocalDateTime.now(clock).truncatedTo(ChronoUnit.MINUTES);

        if (!reservationDateTime.isAfter(now)) {
            throw new IllegalArgumentException("예약은 현재 시각 이후여야 합니다.");
        }
    }

    private void validateDuplicateReservation(String name, LocalDate date, Long timeId) {
        boolean isDuplicate = reservationRepository.existsByNameAndDateAndTime(name, date, timeId);

        if (isDuplicate) {
            throw new DuplicateReservationException("이미 존재하는 예약입니다.");
        }
    }
}
