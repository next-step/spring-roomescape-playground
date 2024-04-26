package roomescape.reservation.business_layer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.time.domain_model_layer.TimeEntity;
import roomescape.reservation.data_access_layer.ReservationDAO;
import roomescape.reservation.domain_model_layer.ReservationEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationDAO reservationDB;
    public List<ReservationEntity> getReservations() {
        return reservationDB.getReservationByDB();
    }

    public ReservationEntity getReservation(Long id) {
        return reservationDB.getReservationByDB(id);
    }

    public ReservationEntity saveReservation(String name, String date, TimeEntity time) {
        ReservationEntity reservation = generateReservation(name, date, time);
        return reservationDB.saveReservationDB(reservation);
    }

    private ReservationEntity generateReservation(String name, String date, TimeEntity time) {
        return ReservationEntity.builder()
                .name(name)
                .date(date)
                .time(time)
                .build();
    }

    public void deleteReservation(Long id) {
        reservationDB.deleteReservationDB(id);
    }
}
