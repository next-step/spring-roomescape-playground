package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            TimeRepository timeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.getReservations();
    }

    public Reservation createReservation(String name, LocalDate date, Long timeId) {

        Time time = timeRepository.getTime(timeId)
                .orElseThrow(NoSuchElementException::new);

        return reservationRepository.saveReservation(
                name,
                date,
                time
        );
    }

    public Reservation getReservation(long id) {
        return reservationRepository.getReservation(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public void deleteReservation(long id) {
        int deletedCount = reservationRepository.deleteReservation(id);

        if (deletedCount == 0) {
            throw new NoSuchElementException();
        }
    }
}
