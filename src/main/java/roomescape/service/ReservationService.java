package roomescape.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;
    private final Clock clock;

    public ReservationService(
            ReservationRepository reservationRepository,
            TimeRepository timeRepository,
            Clock clock
    ) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
        this.clock = clock;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation create(String name, LocalDate date, Long timeId) {
        if (timeId == null) {
            throw new InvalidReservationException();
        }
        ReservationTime reservationTime = timeRepository.findById(timeId)
                .orElseThrow(() -> new NotFoundTimeException(timeId));
        Reservation reservation = Reservation.create(
                name,
                date,
                reservationTime,
                LocalDateTime.now(clock)
        );
        return reservationRepository.save(reservation);
    }

    public void deleteById(Long id) {
        if (!reservationRepository.deleteById(id)) {
            throw new NotFoundReservationException(id);
        }
    }
}
