package roomescape.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationDao.getAllReservations().stream()
            .map(this::convertToResponse)
            .toList();
    }

    public ReservationResponse addReservation(ReservationRequest reservationRequest) {
        Reservation reservation = convertToDomain(reservationRequest);
        reservationDao.saveReservation(reservation);
        return convertToResponse(reservation);
    }

    public void deleteReservation(Long id) {
        reservationDao.removeReservation(id);
    }

    private ReservationResponse convertToResponse(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime()
        );
    }

    private Reservation convertToDomain(ReservationRequest reservationRequest) {
        return new Reservation(
            reservationRequest.id(),
            reservationRequest.name(),
            reservationRequest.date(),
            reservationRequest.time(),
            null
        );
    }
}
