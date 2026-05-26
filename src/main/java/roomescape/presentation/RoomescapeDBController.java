package roomescape.presentation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import roomescape.DAO.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.service.ReservationException;
import roomescape.service.ValidTimeChecker;

@Controller
public class RoomescapeDBController {

    private final ReservationDao reservationDao;
    private final ValidTimeChecker timeChecker;

    public RoomescapeDBController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
        this.timeChecker = new ValidTimeChecker();
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> showAllReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation request) {
        List<LocalDate> reservationDates = reservationDao.findAllDates();
        List<LocalTime> reservationTimes = reservationDao.findAllTimes();

        timeChecker.checkDuplicateException(request.getDate(), request.getTime(), reservationDates, reservationTimes);

        Reservation savedReservation = reservationDao.save(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/reservations/" + savedReservation.getId());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedReservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable int id) {
        int deletedRows = reservationDao.deleteById(id);
        if (deletedRows == 0) {
            throw new ReservationException.NotFoundReservationException("해당 예약이 존재하지 않습니다.");
        }
    }
}
