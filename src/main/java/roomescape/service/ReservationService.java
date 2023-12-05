package roomescape.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationDto> getAllReservations() {
        return reservationDao.getAllReservations().stream()
            .map(this::convertToDto)
            .toList();
    }

    public ReservationDto addReservation(ReservationDto reservationDto) {
        Reservation reservation = convertToDomain(reservationDto);
        reservationDao.saveReservation(reservation);
        return convertToDto(reservation);
    }

    public void deleteReservation(Long id) {
        reservationDao.removeReservation(id);
    }

    private ReservationDto convertToDto(Reservation reservation) {
        return new ReservationDto(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime()
        );
    }

    private Reservation convertToDomain(ReservationDto reservationDto) {
        return new Reservation(
            reservationDto.id(),
            reservationDto.name(),
            reservationDto.date(),
            reservationDto.time()
        );
    }
}
