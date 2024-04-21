package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationDB;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationDB reservationDB;
    public List<Reservation> getReservations() {
        return reservationDB.getReservationByDB();
    }

    public Reservation getReservation(Long id) {
        return reservationDB.getReservationByDB(id);
    }

    public Reservation saveReservation(String name, String date, String time) {
        Reservation reservation = generateReservation(name, date, time);
        return reservationDB.saveReservationDB(reservation);
    }

    private Reservation generateReservation(String name, String date, String time) {
        return Reservation.builder()
                .name(name)
                .date(date)
                .time(time)
                .build();
    }

    public void deleteReservation(Long id) {
        reservationDB.deleteReservationDB(id);
    }
}
