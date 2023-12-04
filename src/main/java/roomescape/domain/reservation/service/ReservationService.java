package roomescape.domain.reservation.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.reservation.dao.ReservationRepository;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.time.dao.TimeRepository;
import roomescape.domain.time.entity.Time;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation createReservation(String name, LocalDate date, long timeId) {
        Time time = timeRepository.findById(timeId);
        Reservation reservation = generateReservation(name, date, time);
        return reservationRepository.save(reservation);
    }

    private Reservation generateReservation(String name, LocalDate date, Time time) {
        return Reservation.builder()
                .name(name)
                .date(date)
                .time(time)
                .build();
    }

    @Transactional
    public void deleteReservation(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
