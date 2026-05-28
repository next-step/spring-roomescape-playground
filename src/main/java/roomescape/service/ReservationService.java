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
        validateDuplicatedReservation(request);

        Reservation newReservation = new Reservation(
                null,
                request.name(),
                request.date().toString(),
                request.time()
        );
        return reservationRepository.save(newReservation);
    }

    public void deleteReservation(Long id) {
        if (!reservationRepository.deleteById(id)) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }

    private void validateDuplicatedReservation(ReservationRequest request) {
        if (reservationRepository.existsByDateAndTime(request.date().toString(), request.time())) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }
    }
}
