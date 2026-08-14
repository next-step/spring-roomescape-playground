package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateCommand;
import roomescape.exception.BadRequestException;
import roomescape.exception.ReservationConflictException;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final Clock clock;

    public ReservationService(ReservationRepository reservationRepository, Clock clock) {
        this.reservationRepository = reservationRepository;
        this.clock = clock;
    }

    public synchronized List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public synchronized Reservation addReservation(ReservationCreateCommand command) {
        validateNotPast(command);
        validateNotDuplicated(command);

        Reservation reservation = new Reservation(
                command.name(),
                command.date(),
                command.time()
        );

        return reservationRepository.save(reservation);
    }

    public synchronized void deleteReservation(Long id) {
        boolean deleted = reservationRepository.deleteById(id);

        if (!deleted) {
            throw new ReservationNotFoundException("해당 예약을 찾을 수 없습니다.");
        }
    }

    private void validateNotPast(ReservationCreateCommand command) {
        LocalDateTime reservationDateTime = LocalDateTime.of(command.date(), command.time());

        if (reservationDateTime.isBefore(LocalDateTime.now(clock))) {
            throw new BadRequestException("과거 시간은 예약할 수 없습니다.");
        }
    }

    private void validateNotDuplicated(ReservationCreateCommand command) {
        if (reservationRepository.existsByDateAndTime(command.date(), command.time())) {
            throw new ReservationConflictException("이미 예약된 시간입니다.");
        }
    }
}
