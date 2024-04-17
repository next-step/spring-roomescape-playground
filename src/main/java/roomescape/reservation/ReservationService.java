package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ReservationDTO createReservationDTO(long id, ReservationDTO reservationDTO) {
        return new ReservationDTO(id, reservationDTO.getName(), reservationDTO.getDate(), reservationDTO.getTime());
    }

    public ReservationDTO updateReservationDTO(long id, ReservationDTO reservationDTO) {
        return createReservationDTO(id, reservationDTO);
    }

}
