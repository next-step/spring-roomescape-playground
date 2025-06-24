package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestException;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void removeReservation(int id) {
        boolean removed = reservationRepository.delete(id);
        if (!removed) {
            throw new BadRequestException("요청한 id에 해당하는 예약이 존재하지 않습니다");
        }
    }

    public Reservation addReservation(Reservation reservation) {
        verification(reservation);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    // 검증 메서드 분리
    private void verification(Reservation reservation) {
        if (reservation.name() == null || reservation.name().isBlank()
                || reservation.date() == null || reservation.date().isBlank()
                || reservation.time() == null || reservation.time().isBlank()) {
            throw new BadRequestException("입력값 null 이거나 빈 값 입니다 ");
        }
    }
}
