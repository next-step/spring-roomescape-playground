package roomescape.validator;

import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Component
public class ReservationValidator {

    private final ReservationRepository reservationRepository;

    public ReservationValidator(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void validateDuplicate(Reservation target) {
        List<Reservation> reservationList = reservationRepository.findByDate(target.getDate());

        Reservations targetDateReservations = new Reservations(reservationList);
        targetDateReservations.validateDuplicate(target);
    }
}
