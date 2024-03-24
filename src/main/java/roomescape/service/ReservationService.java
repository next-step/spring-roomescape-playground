package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.repository.dao.ReservationDao;
import roomescape.repository.domain.Reservation;
import roomescape.repository.domain.Time;
import roomescape.dto.ReservationDTO;
import roomescape.dto.TimeDTO;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationDTO> getAllReservations() {
        List<Reservation> reservations = reservationDao.getAllReservations();
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO insertReservation(ReservationDTO reservationDTO) {
        Reservation reservation = convertToEntity(reservationDTO);
        Reservation newReservation = reservationDao.insertReservation(reservation);
        return convertToDTO(newReservation);
    }

    public void deleteReservationById(Long id) {
        reservationDao.deleteReservationById(id);
    }

    private ReservationDTO convertToDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new TimeDTO(reservation.getTime().getId(), reservation.getTime().getTime())
        );
    }

    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        return new Reservation(
                reservationDTO.getId(),
                reservationDTO.getName(),
                reservationDTO.getDate(),
                new Time(reservationDTO.getTime().getId(), reservationDTO.getTime().getTime())
        );
    }
}

