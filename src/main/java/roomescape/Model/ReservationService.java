package roomescape.Model;

import org.springframework.stereotype.Service;
import roomescape.Repository.ReservationRepository;
import java.util.List;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservationsList(){
        List<Reservation> reservationList=reservationRepository.getAllReservations();
        return reservationList;
    }

    public Reservation saveReservation(RequestReservation reservation){
        String name=reservation.getName();
        String date=reservation.getDate();
        String time=reservation.getTime();

        Reservation newReservation=new Reservation(null, name, date,time);
        Long id=reservationRepository.createReservation(newReservation);
        return reservationRepository.getReservationById(id);
    }

    public void deleteReservation(Long id){
        reservationRepository.deleteReservationById(id);
    }

    public Reservation viewReservation(Long id){
        Reservation findReservation=reservationRepository.getReservationById(id);
        return findReservation;
    }
}
