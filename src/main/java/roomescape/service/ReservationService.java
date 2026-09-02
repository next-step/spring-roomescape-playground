package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationCreateCommand;
import roomescape.exception.ReservationErrorCode;
import roomescape.exception.ReservationException;
import roomescape.exception.TimeErrorCode;
import roomescape.exception.TimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;
    private final Clock clock;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository, Clock clock) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
        this.clock = clock;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationCreateCommand command) {
        Time time = timeRepository.findById(command.timeId())
                .orElseThrow(() -> new TimeException(TimeErrorCode.TIME_NOT_FOUND));

        validateNotPast(command.date(), time.getStartAt());
        validateNotDuplicate(command.date(), time.getId());

        Reservation reservation = new Reservation(
                command.name(),
                command.date(),
                time
        );

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        boolean deleted = reservationRepository.deleteById(id);

        if (!deleted) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND);
        }
    }

    private void validateNotPast(LocalDate date, LocalTime startAt) {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, startAt);

        if (reservationDateTime.isBefore(LocalDateTime.now(clock))) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_IN_PAST);
        }
    }

    private void validateNotDuplicate(LocalDate date, Long timeId) {
        if (reservationRepository.existsByDateAndTimeId(date, timeId)) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_CONFLICT);
        }
    }
}
