package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequestDto;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationDao;
import roomescape.repository.TimeDao;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;


    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAllReservations();
    }

    public Reservation createReservation(ReservationRequestDto requestDto) {
        Time time = timeDao.findById(requestDto.getTime());

        Reservation reservation = new Reservation(
                null,
                requestDto.getName(),
                LocalDate.parse(requestDto.getDate()),
                time
        );

        Long id = reservationDao.insert(reservation);

        return Reservation.toEntity(reservation, id);
    }

    public void deleteReservation(Long id) {
        int deleteNumber = reservationDao.deleteReservation(id);

        if (deleteNumber == 0) {
            throw new NotFoundReservationException(
                    "Reservation not found: id=" + id
            );
        }
    }
}
