package roomescape.web.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Model.Reservation;
import roomescape.domain.Repository.ReservationRepository;
import roomescape.domain.exception.NoDataException;
import roomescape.domain.exception.NotFoundReservationException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation join(Reservation reservation) {
        validateReservation(reservation);
        return reservationRepository.save(reservation);
    }

    private void validateReservation(Reservation reservation) {
        if(reservation.getTime().isBlank() || reservation.getDate().isBlank() || reservation.getName().isBlank())
            throw new NoDataException("데이터가 비어있습니다.");
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation delete(Long id){
        return reservationRepository.deleteReservation(id);
    }
}
