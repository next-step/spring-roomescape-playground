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
public class ReservationApiService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public List<Reservation> loadReservationList(){
//        reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2022-08-05", "15:40"));
//        reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2022-08-05", "18:40"));
//        reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2022-08-05", "20:40"));
        return reservations;
    }

    public Reservation createReservation(Reservation reservation){
        Reservation newReservation = Reservation.builder()
                .id(index.incrementAndGet())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build();
        if (newReservation.getName().isEmpty()){
            throw new NoParameterException("Reservation Have No Name Parameter");
        } else if (newReservation.getDate().isEmpty()){
            throw new NoParameterException("Reservation Have No Date Parameter");
        } else if (newReservation.getTime().isEmpty()){
            throw new NoParameterException("Reservation Have No Time Parameter");
        }
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
