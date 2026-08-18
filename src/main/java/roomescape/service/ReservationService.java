package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final Clock clock;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this(reservationRepository, Clock.systemDefaultZone());
    }

    ReservationService(ReservationRepository reservationRepository, Clock clock) {
        this.reservationRepository = reservationRepository;
        this.clock = clock;
    }

    public Reservation create(String name, LocalDate date, LocalTime time) {
        Reservation reservation = new Reservation(name, date, time);

        validateReservationDateTime(date, time);
        validateDuplicateReservation(name, date, time);

        return reservationRepository.save(reservation);
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

    private void validateDuplicateReservation(String name, LocalDate date, LocalTime time) {
        boolean exists = reservationRepository.existsByNameAndDateAndTime(name, date, time);

        if (exists) {
            throw new DuplicateReservationException("이미 존재하는 예약입니다.");
        }
    }
}
