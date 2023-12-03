package roomescape.reservation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ReservationService {


    private final  ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    @Transactional
    public List<Reservation> getReservations(){
        return reservationRepository.SelectAll();
    }
    @Transactional
    public Reservation postReservations(Reservation reservation) {
        return reservationRepository.selectReservation(reservation);
    }

    @Transactional
    public void deleteReservations(Long id) {
        reservationRepository.DeleteById(id);
    }
}
