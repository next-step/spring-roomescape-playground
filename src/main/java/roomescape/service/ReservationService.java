package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequestDto;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationDao;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAllReservations();
    }

    public Reservation createReservation(ReservationRequestDto requestDto) {
        Time time = new Time((long) requestDto.getTime(), null);

        Reservation reservation = new Reservation(
                null,
                requestDto.getName(),
                requestDto.getDate(),
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
