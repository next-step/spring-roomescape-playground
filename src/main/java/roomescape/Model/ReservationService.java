package roomescape.Model;


import org.springframework.stereotype.Service;
import roomescape.Exception.NotFoundReservationException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {
    private AtomicLong atomicLong = new AtomicLong(0);

    public ReservationService(){

    }

    public Reservation saveReservation(RequestReservation reservation, List<Reservation> reservations){

        String name=reservation.getName();
        String date=reservation.getDate();
        String time=reservation.getTime();

        Reservation newReservation=new Reservation(atomicLong.incrementAndGet(), name, date,time);
        reservations.add(newReservation);

        return newReservation;
    }

    public void deleteReservation(List<Reservation> reservations,Long id){

        boolean res=reservations.removeIf(reservation -> reservation.getId()==id);
        if(!res)
            throw new NotFoundReservationException("예약을 찾을 수 없습니다.");
    }

    public Reservation viewReservation(List<Reservation> reservations,Long id){
        for(Reservation reservation:reservations)
            if(reservation.getId()==id)
                return reservation;
        throw new NotFoundReservationException("예약을 찾을 수 없습니다.");
    }

}
