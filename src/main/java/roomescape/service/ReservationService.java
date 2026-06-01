package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationGetResponse;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.NotFoundReservationException;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao repository;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao repository, TimeDao timeDao) {
        this.repository = repository;
        this.timeDao = timeDao;
    }


    public List<ReservationGetResponse> getReservations() {
        List<Reservation> reservations = repository.findAll();
        return reservations.stream()
                .map(it -> new ReservationGetResponse(
                        it.getId(),
                        it.getName(),
                        it.getDate(),
                        it.getTime().getId(),
                        it.getTime().getTime()))
                .toList();
    }

    public ReservationCreateResponse addReservation(ReservationCreateRequest reservationCreateRequest) {
        Time time = timeDao.findById(reservationCreateRequest.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));

        boolean exists = repository.existsByDateAndTime(reservationCreateRequest.getDate(), time.getTime());
        if (exists) {
            throw new DuplicateReservationException("이미 예약된 시간입니다.");
        }

        Reservation newReservation = new Reservation(
                reservationCreateRequest.getName(),
                reservationCreateRequest.getDate(),
                time);

        newReservation = repository.save(newReservation);

        return new ReservationCreateResponse(
                newReservation.getId(),
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime());
    }

    public void deleteReservation(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundReservationException();
        }
    }
}
