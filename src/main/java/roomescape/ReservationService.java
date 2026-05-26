package roomescape;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public Reservation.Response createReservation(Reservation.Request request) {
        if (request.getName() == null || request.getDate() == null || request.getTimeId() == null) {
            throw new InvalidReservationException("필수 예약 정보가 누락되었습니다.");
        }
        if (request.getDate().isBefore(LocalDate.now())) {
            throw new InvalidReservationException("과거 날짜로는 예약할 수 없습니다.");
        }

        Time time = timeDao.findById(request.getTimeId());
        if (time == null) {
            throw new NotFoundTimeException(request.getTimeId());
        }

        if (reservationDao.existsByDateAndTimeId(request.getDate(), time.getId())) {
            throw new DuplicateReservationException(request.getDate().toString(), time.getTime().toString());
        }

        Reservation reservation = new Reservation(null, request.getName(), request.getDate(), time);
        Long generatedId = reservationDao.insert(reservation);

        return new Reservation.Response(Reservation.toEntity(reservation, generatedId));
    }

    public List<Reservation.Response> findAllReservations() {
        return reservationDao.findAll().stream()
                .map(Reservation.Response::new)
                .toList();
    }

    public void deleteReservation(Long id) {
        int updatedRows = reservationDao.deleteById(id);
        if (updatedRows == 0) {
            throw new NotFoundReservationException(id);
        }
    }
}
