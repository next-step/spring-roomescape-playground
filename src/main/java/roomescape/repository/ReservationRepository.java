package roomescape.repository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {
    private final static List<Reservation> reservations = new ArrayList<>();
    public final static AtomicLong index = new AtomicLong(1);
    public List<Reservation> findAll(){
        return reservations;
    }

    public Reservation createReservation(Reservation reservation){
        reservations.add(reservation);
        return reservation;
    }

    public void deleteReservation(Reservation reservation){
        reservations.remove(reservation);
    }
}
