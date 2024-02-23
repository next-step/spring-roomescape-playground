package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.Reservation;
import roomescape.exception.NoParameterException;
import roomescape.exception.NotFoundReservationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public List<Reservation> loadReservationList(){
        return reservations;
    }

    public Reservation createReservation(Reservation reservation){
        if (reservation.getName().isEmpty()){
            throw new NoParameterException("Reservation Have No Name Parameter");
        } else if (reservation.getDate().isEmpty()){
            throw new NoParameterException("Reservation Have No Date Parameter");
        } else if (reservation.getTime().isEmpty()){
            throw new NoParameterException("Reservation Have No Time Parameter");
        }
        Reservation newReservation = Reservation.builder()
                .id(index.incrementAndGet())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build();

        reservations.add(newReservation);
        return newReservation;
    }

    public void deleteReservation(Long id){
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("Not Found Reservation"));
        reservations.remove(reservation);
    }
}
