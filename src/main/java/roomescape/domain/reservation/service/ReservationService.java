package roomescape.domain.reservation.service;

import static roomescape.domain.reservation.exception.ReservationException.ErrorCode.DUPLICATED;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.dao.SimpleReservationRepository;
import roomescape.domain.reservation.exception.ReservationException;
import roomescape.domain.reservation.model.Reservation;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final SimpleReservationRepository reservationRepository;

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(String name, LocalDate date, LocalTime time) {
        Reservation reservation = generateReservation(name, date, time);
        validateDuplicate(reservation);
        return reservationRepository.save(reservation);
    }

    private Reservation generateReservation(String name, LocalDate date, LocalTime time) {
        return Reservation.builder()
                .name(name)
                .date(date)
                .time(time)
                .build();
    }

    private void validateDuplicate(Reservation reservation) {
        List<Reservation> reservations = reservationRepository.findAll();
        boolean isDuplicate = reservations.stream().anyMatch(r -> r.isNameEquals(reservation));
        if (isDuplicate) {
            throw new ReservationException(DUPLICATED);
        }
    }

    public void deleteReservation(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
