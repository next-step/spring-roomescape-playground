package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dataLayer.ReservationRepository;
import roomescape.dto.ReservationDto;
import roomescape.model.Reservation;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(ReservationDto reservationDto) {
        Long newReservationId = reservationRepository.add(reservationDto.name(), reservationDto.date(), reservationDto.time_id());
        return reservationRepository.getReservationById(newReservationId);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.getReservations();
    }

    public void deleteReservationById(Long deletingId) {
        reservationRepository.removeById(deletingId);
    }
}
