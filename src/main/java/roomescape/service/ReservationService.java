package roomescape.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.ReservationTimeNotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {
    private final Clock clock;
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(Clock clock, ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.clock = clock;
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Reservation createReservation(String name, LocalDate date, Long timeId) {
        ReservationTime reservationTime = reservationTimeRepository.findById(timeId)
                .orElseThrow(() -> new ReservationTimeNotFoundException("예약하려는 시간이 존재하지 않습니다."));
        Reservation reservation = Reservation.createNewReservation(name, date, reservationTime, clock);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
