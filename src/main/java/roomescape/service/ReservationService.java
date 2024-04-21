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

    public Long saveReservation(Reservation reservation) {
        return reservationDB.saveReservationDB(reservation);
    }

    public void deleteReservation(Long id) {
        reservationDB.deleteReservationDB(id);
    }
}
