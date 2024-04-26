package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.DTO.ReservationDTO;
import roomescape.Domain.Reservation;
import roomescape.Domain.Time;
import roomescape.Repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    public List<Reservation> allReservation(){
        return reservationRepository.getAllReservation();
    }

    public Reservation createdReservation(ReservationCreateRequestDTO reservationCreateRequest) {
        String time = reservationRepository.getTimeById(reservation.time());
        Reservation request = new Reservation(null, reservation.name(), reservation.date(), new Time(reservation.time(), time));
        return reservationRepository.createdReservation(request);
    }

    public void deletedReservation(Long id){
        reservationRepository.deleteReservationById(id);
    }
}