package roomescape.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.Repository.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateForm;
import roomescape.dto.ReservationResponseForm;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseForm> getAllReservations() {
        try {
            List<Reservation> reservations = reservationRepository.findAll();
            return reservations.stream()
                    .map(ReservationResponseForm::new)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("예약을 가져올 수 없습니다", e);
        }
    }

    public ReservationResponseForm createReservation(ReservationCreateForm form) {
        try {
            Reservation newReservation = form.toEntity();
            Long newId = reservationRepository.save(newReservation);
            Reservation reservation = reservationRepository.findById(newId);

            return new ReservationResponseForm(reservation);
        } catch (Exception e) {
            throw new RuntimeException("예약을 생성할 수 없습니다", e);
        }
    }

    public void deleteReservation(Long id) {
        try {
            reservationRepository.cancel(id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("예약된 아이디가 없습니다 id: " + id, e);
        } catch (Exception e) {
            throw new RuntimeException("예약을 지울수 없습니다", e);
        }
    }
}

