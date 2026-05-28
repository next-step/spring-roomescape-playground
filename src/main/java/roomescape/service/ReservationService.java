package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationValidator;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationValidator validator;

    public ReservationService(ReservationRepository reservationRepository, ReservationValidator validator) {
        this.reservationRepository = reservationRepository;
        this.validator = validator;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationRequest request) {
        validator.validateReservationDateTime(request.date(), request.time());
        validator.validateDuplicatedReservation(request.date(), request.time(), reservationRepository.findAll());

        Reservation newReservation = new Reservation(
                null,
                request.name(),
                request.date().toString(),
                request.time()
        );
        return reservationRepository.save(newReservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
