package roomescape.domain.reservation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.dao.ReservationDAO;
import roomescape.global.exception.ReservationInvalidArgumentException;
import roomescape.global.exception.ReservationNotFoundException;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDAO reservationRepository;

    public ReservationServiceImpl(ReservationDAO reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional
    public Reservation addReservation(Reservation reservation) {
        if (reservation.getDate().isBefore(LocalDate.now()) || reservation.getDate().isEqual(LocalDate.now()))
            throw new ReservationInvalidArgumentException(HttpStatus.BAD_REQUEST, "당일예약 및 과거날짜는 예약할 수 없습니다.");
        return getReservationById(reservationRepository.addReservation(reservation));
    }

    @Override
    @Transactional
    public void deleteReservation(Long id) {
        if (!reservationRepository.deleteReservation(id))
            throw new ReservationNotFoundException(HttpStatus.BAD_REQUEST, "해당 id의 예약은 존재하지 않습니다.");
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.getReservationById(id)
            .orElseThrow(() -> new ReservationNotFoundException(HttpStatus.BAD_REQUEST, "해당 id의 예약은 존재하지 않습니다."));
    }

    @Override
    public List<Reservation> getAllReservation() {
        return reservationRepository.getAllReservation();
    }
}
