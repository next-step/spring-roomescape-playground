package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.JdbcReservationRepository;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(String name, LocalDate date, LocalTime time) {
        return reservationRepository.save(name, date, time);
    }

    public void deleteReservation(long id) {
        boolean deleteCount = reservationRepository.deleteById(id);

        if (!deleteCount) {
            throw new NotFoundReservationException("해당 id의 예약을 찾을 수 없습니다.");
        }
    }
}
