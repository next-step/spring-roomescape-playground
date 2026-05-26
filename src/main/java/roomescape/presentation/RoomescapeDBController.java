package roomescape.presentation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import roomescape.DAO.ReservationDao;
import roomescape.DAO.TimeDao;
import roomescape.domain.Reservation;
import roomescape.service.ReservationException;
import roomescape.service.ValidTimeChecker;

@Controller
public class RoomescapeDBController {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;
    private final ValidTimeChecker timeChecker;

    public RoomescapeDBController(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
        this.timeChecker = new ValidTimeChecker(timeDao);
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> showAllReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        return ResponseEntity.ok().body(reservations);
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<LocalTime>> showAllValidTimes(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        timeDao.refreshValidTimesScheduler();

        List<LocalTime> times = timeDao.findAllValidTimes(date);
        return ResponseEntity.ok().body(times);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation request) {

        timeChecker.checkReservationable(request.getDate(), request.getTime());

        Reservation savedReservation = reservationDao.saveReservation(request);
        timeDao.deleteValidTime(request.getDate(), request.getTime());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/reservations/" + savedReservation.getId());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedReservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable int id) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new ReservationException.NotFoundReservationException("해당 예약이 존재하지 않습니다.");
        }

        reservationDao.deleteById(id);
        timeDao.saveValidTime(reservation.getDate(), reservation.getTime());
    }
}
