package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Times;
import roomescape.dto.ReservationResponse;
import roomescape.dto.SaveReservationRequest;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private TimeService timeService;

    public ReservationService(ReservationRepository reservationRepository, TimeService timeService) {
        this.reservationRepository = reservationRepository;
        this.timeService = timeService;
    }

    public ReservationResponse saveReservation(SaveReservationRequest request){
        Times time = timeService.findById(request.timeId());
        Reservation reservation = reservationRepository.save(request.toReservation(time));
        return ReservationResponse.from(reservation);
    }

    public List<Reservation> findReservationById() {
        return reservationRepository.findAll();
    }

    public void deleteById(int id) {
        reservationRepository.deleteById(id);
    }
}
