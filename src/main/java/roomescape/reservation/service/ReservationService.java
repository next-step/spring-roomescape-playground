package roomescape.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.ReservationDAO;
import roomescape.reservation.ReservationDTO;
import roomescape.reservation.ReservationDTOMapper;

import java.util.List;

@Service
public class ReservationService {

    private ReservationDAO reservationDAO;
    private ReservationDTOMapper reservationDTOMapper;

    @Autowired
    public ReservationService(ReservationDAO reservationDAO, ReservationDTOMapper reservationDTOMapper) {
        this.reservationDAO = reservationDAO;
        this.reservationDTOMapper = reservationDTOMapper;
    }

    public List<ReservationDTO> read() {
        List<Reservation> reservations = reservationDAO.findAllReservations();
        return reservationDTOMapper.convertToDtoList(reservations);
    }

    public ReservationDTO insertReservationDTO(ReservationDTO reservationDTO) {
        Long id = reservationDAO.insertWithKeyHolder(reservationDTO);
        return new ReservationDTO(id, reservationDTO.getName(), reservationDTO.getDate(), reservationDTO.getTime());
    }

//    public void updateReservationDTO(long id, ReservationDTO reservationDTO) {
//        reservationDAO.update(id, reservationDTO);
//    }

    public void deleteReservationDTO(long id) {
        reservationDAO.delete(id);
    }
}
