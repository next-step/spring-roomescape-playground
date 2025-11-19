package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong; // 1. ID 생성을 위해 추가

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation addReservation(Reservation newReservation) {

        return reservationRepository.save(newReservation);
    }


    public void deleteReservation(Long id) {
        boolean exists = reservationRepository.existsById(id);

        if (!exists) {
            throw new IllegalArgumentException("존재하지 않는 예약 ID입니다: " + id);
        }

        reservationRepository.deleteById(id);
    }

    public void clear() {
        reservationRepository.clear();
    }
}
