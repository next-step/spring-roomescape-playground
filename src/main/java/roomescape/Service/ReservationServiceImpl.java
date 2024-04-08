package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import roomescape.Dao.ReservationDao;
import roomescape.Domain.Reservation;
import roomescape.Domain.Time;
import roomescape.Exception.NotFoundReservationException;
import roomescape.Service.ReservationService;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;

    @Autowired
    public ReservationServiceImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationDao.getAllReservations();
    }

    @Override
    public ResponseEntity<Reservation> addReservation(Reservation reservation) {
        validateReservation(reservation);

        long newId = reservationDao.addReservation(reservation);

        Reservation addedReservation = reservationDao.getReservationById(newId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/reservations/" + newId);

        return new ResponseEntity<>(addedReservation, headers, HttpStatus.CREATED);
    }

    @Override
    public void cancelReservation(Long id) {
        int rowsAffected = reservationDao.cancelReservation(id);
        if (rowsAffected == 0) {
            throw new NotFoundReservationException("Reservation not found with id: " + id);
        }
    }

    private void validateReservation(Reservation reservation) {
        if (reservation == null ||
                reservation.getName() == null || reservation.getName().isEmpty() ||
                reservation.getDate() == null || reservation.getDate().isEmpty() ||
                reservation.getTimeId() <= 0) {
            throw new NotFoundReservationException("Required fields are missing.");
        }
    }
}