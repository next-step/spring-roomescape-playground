package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    final List<Reservation> reservations = new ArrayList<>();

    @Autowired
    private QueryDAO queryDAO;


//    public ReservationService(){
//            // 데이터 추가
//            reservations.add(new Reservation(1L, "브라운", "2023-1-1", "10:0"));
//            reservations.add(new Reservation(2L, "브라운", "2023-1-2", "11:0"));
//            reservations.add(new Reservation(3L, "브라운", "2023-1-3", "12:0"));
//    }

    public List<Reservation> getAllReservations() {
        return queryDAO.findAllReservations();
    }

    public void addReservation(Reservation reservation){
        queryDAO.addReservation(reservation);
    }

    public void deleteReservation(Long id){
        queryDAO.deleteReservation(id);
        return reservations;

    }



}
