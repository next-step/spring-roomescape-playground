package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(String name, LocalDate date, long timeId) {
        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new NotFoundTimeException("해당 id의 시간을 찾을 수 없습니다."));

        Reservation reservation = new Reservation(name, date, time);
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(long id) {
        boolean deleted = reservationRepository.deleteById(id);

        if (!deleted) {
            throw new NotFoundReservationException("해당 id의 예약을 찾을 수 없습니다.");
        }
    }
}
