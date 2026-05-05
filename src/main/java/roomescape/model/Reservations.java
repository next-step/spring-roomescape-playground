package roomescape.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Reservations {
    private final List<Reservation> reservations;

    public Reservations(){
        this.reservations = new ArrayList<Reservation>();
    }

    @Bean
    public Reservations reservationsAsBean() {
        return new Reservations();
    }

    public List<Reservation> getReservationList(){
        List<Reservation> copiedReservation = new ArrayList<>();
        for (Reservation reservation: this.reservations) {
            copiedReservation.add(reservation.copy());
        }
        return List.copyOf(copiedReservation);
    }

    public void add(Reservation newReservation){
        this.reservations.add(newReservation);
    }

    public void removeById(int deletingId){
        Reservation toDelete = this.reservations.stream()
                .filter(reservation -> deletingId == reservation.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Wrong Index"));

        this.reservations.remove(toDelete);
    }
}

