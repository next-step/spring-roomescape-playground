package roomescape.reservation.application;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationRepository;
import roomescape.reservation.presentation.dto.SaveReservationRequest;
import roomescape.time.domain.Time;
import roomescape.time.domain.TimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(@Qualifier("jdbcReservationRepository") final ReservationRepository reservationRepository,
                              final TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation save(final SaveReservationRequest request) {
        final Time time = timeRepository.findById(request.time());
        final Reservation reservation = new Reservation(request.name(), request.date(), time);
        return reservationRepository.save(reservation);
    }

    public void deleteById(final Long id) {
        reservationRepository.deleteById(id);
    }
}
