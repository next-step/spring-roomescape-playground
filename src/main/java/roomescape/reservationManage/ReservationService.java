package roomescape.reservationManage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservationManage.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation addReservation(String name, String date, Long time) {
        return reservationRepository.save(name, date, time);
    }

    @Transactional
    public void cancelReservation(Long id) {
        reservationRepository.delete(id);
    }
}
