package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationRepository;

import java.util.List;

@Component
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(ReservationDto reservationDto) {
        Long newReservationId = reservationRepository.add(reservationDto);
        return reservationRepository.getReservationById(newReservationId);
    }

    public List<Reservation> getReservationList() {
        return reservationRepository.getReservationList();
    }

    public void deleteReservationById(Long deletingId) {
        reservationRepository.removeById(deletingId);
    }
}
