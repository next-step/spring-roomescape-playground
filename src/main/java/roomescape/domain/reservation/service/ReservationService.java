package roomescape.domain.reservation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.dao.ReservationRepository;
import roomescape.domain.reservation.entity.Reservation;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(String name, LocalDate date, LocalTime time) {
        Reservation reservation = generateReservation(name, date, time);
        return reservationRepository.save(reservation);
    }

    private Reservation generateReservation(String name, LocalDate date, LocalTime time) {
        return Reservation.builder()
                .name(name)
                .date(date)
                .time(time)
                .build();
    }

    public void deleteReservation(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
