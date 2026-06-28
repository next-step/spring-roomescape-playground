package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;
    public ReservationService(ReservationRepository reservationRepository,TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> getReservation() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        Time time = timeRepository.findById(reservationRequest.time().id())
                .orElseThrow(()->new NotFoundTimeException());
        Reservation reservation = new Reservation(
                null,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
        long id = reservationRepository.insert(reservation);
        return new Reservation(
                id,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
    }

    public void cancelReservation(Long id) {
        boolean isRemoved = reservationRepository.delete(id);

        if (!isRemoved) {
            throw new NotFoundReservationException();
        }
    }

}
