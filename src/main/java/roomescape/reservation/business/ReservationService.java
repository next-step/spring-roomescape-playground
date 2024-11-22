package roomescape.reservation.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.presentation.dto.ReservationDto;
import roomescape.reservation.persistence.ReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;

    public Reservation addReservation(ReservationDto reservationDto) {
        Reservation reservation = convertToEntity(reservationDto);
        return repository.save(reservation);
    }

    public List<Reservation> checkReservations() {
        return repository.findAll();
    }

    public void deleteReservation(Long reservationId) {
        repository.delete(reservationId);
    }

    private Reservation convertToEntity(ReservationDto reservationDto) {
        return new Reservation(null, reservationDto.name(), reservationDto.date(), reservationDto.time());
    }
}
