package roomescape.service;

import static io.micrometer.common.util.StringUtils.isBlank;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.reservation.ReservationRequest;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long saveReservation(ReservationRequest reservationRequest) {
        if(isBlank(reservationRequest.getName()) || reservationRequest.getDate() == null || reservationRequest.getTime() == null) {
            throw new InvalidReservationException("이름, 날짜, 시간은 입력해야합니다.");
        }
        Reservation reservation = new Reservation(null,
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationRequest.getTime());
        return reservationRepository.saveReservation(reservation);
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findReservationById(id);
    }

    public List<Reservation> findAllReservation() {
        return reservationRepository.findAllReservation();
    }

    public void deleteReservationById(Long id) {
        try {
            reservationRepository.findReservationById(id);
            reservationRepository.deleteReservationById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ReservationNotFoundException("해당 id의 예약을 찾을 수 없습니다");
        }
    }
}
