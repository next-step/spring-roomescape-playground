package roomescape.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.exception.InvalidReservationRequestException;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final Clock clock;

    public ReservationService(ReservationRepository reservationRepository, Clock clock) {
        this.reservationRepository = reservationRepository;
        this.clock = clock;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation create(Reservation reservation) {
        LocalDateTime reservationDateTime = LocalDateTime.of(
                reservation.getDate(),
                reservation.getTime()
        );
        if (reservationDateTime.isBefore(LocalDateTime.now(clock))) {
            throw new InvalidReservationRequestException("지난 일시로는 예약할 수 없습니다.");
        }
        return reservationRepository.save(reservation);
    }

    public void deleteById(Long id) {
        if (!reservationRepository.deleteById(id)) {
            throw new NotFoundReservationException(id);
        }
    }
}
