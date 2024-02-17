package roomescape.domain.reservation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.global.exception.ReservationInvalidArgumentException;
import roomescape.global.exception.ReservationNotFoundException;

@Service
public class ReservationServiceImpl implements ReservationService{

    ReservationRepository reservationRepository = new ReservationRepository();

    @Override
    public Reservation addReservation(Reservation reservation) {
        LocalDateTime localDateTime = LocalDateTime.of(reservation.getDate(), reservation.getTime());
        if(localDateTime.isBefore(LocalDateTime.now())) throw new ReservationInvalidArgumentException(HttpStatus.BAD_REQUEST, "날짜 및 시간은 과거가 될 수 없습니다.");
        Reservation newReservation = Reservation.toEntity(reservation, reservationRepository.index.getAndIncrement());
        reservationRepository.add(newReservation);
        return newReservation;
    }

    @Override
    public void deleteReservation(Long id) {
        if(!reservationRepository.removeIf(reservation -> reservation.getId().equals(id)))
            throw new ReservationNotFoundException(HttpStatus.BAD_REQUEST, "해당 id의 예약은 존재하지 않습니다.");
    }

    @Override
    public Reservation getReservationById(Long id) {
        Reservation foundReservation = reservationRepository.stream()
            .filter(reservation -> reservation.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ReservationNotFoundException(HttpStatus.BAD_REQUEST, "해당 id의 예약은 존재하지 않습니다."));
        return foundReservation;
    }

    @Override
    public List<Reservation> getAllReservation() {
        return reservationRepository;
    }
}
