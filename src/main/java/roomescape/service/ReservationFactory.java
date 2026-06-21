package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationDto;
import roomescape.model.Reservation;
import roomescape.model.Reservations;
import roomescape.model.Time;

import java.util.List;

@Component
public class ReservationFactory {
    private final Reservations reservations;
    private final TimeFactory timeFactory;

    @Autowired
    public ReservationFactory(Reservations reservations, TimeFactory timeFactory) {
        this.reservations = reservations;
        this.timeFactory = timeFactory;
    }

    public ReservationDto createReservation(ReservationDto reservationDto) {
        Long newReservationId = reservations.add(reservationDto);
        Reservation newReservation = reservations.getReservationById(newReservationId);

        return new ReservationDto(newReservation);
    }

    public List<Reservation> getReservationList(){
        return reservations.getReservationList();
    }

    public void deleteReservationById(Long deletingId) {
        reservations.removeById(deletingId);
    }
}
