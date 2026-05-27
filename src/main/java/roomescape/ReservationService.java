package roomescape;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.TimeResponse;

import java.util.List;
import roomescape.exception.DuplicateReservationException;
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

    public ReservationResponse createReservation(ReservationRequest request) {

        Time time = timeDao.findById(request.getTimeId())
                .orElseThrow(() -> new NotFoundTimeException(request.getTimeId()));

        if (reservationDao.existsByDateAndTimeId(request.getDate(), time.getId())) {
            throw new DuplicateReservationException(request.getDate().toString(), time.getTime().toString());
        }

        Reservation reservation = new Reservation(null, request.getName(), request.getDate(), time);
        Long generatedId = reservationDao.insert(reservation);

        TimeResponse timeResponse = new TimeResponse(time.getId(), time.getTime().toString());
        return new ReservationResponse(generatedId, reservation.getName(), reservation.getDate(), timeResponse);
    }

    public List<ReservationResponse> findAllReservations() {
        return reservationDao.findAll().stream()
                .map(res -> new ReservationResponse(
                        res.getId(),
                        res.getName(),
                        res.getDate(),
                        new TimeResponse(res.getTime().getId(), res.getTime().getTime().toString())
                ))
                .toList();
    }

    public void deleteReservation(Long id) {
        int updatedRows = reservationDao.deleteById(id);
        if (updatedRows == 0) {
            throw new NotFoundReservationException(id);
        }
    }
}
