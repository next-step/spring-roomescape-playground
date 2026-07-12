package roomescape.api.reservations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.api.reservations.dto.response.ReservationResponse;
import roomescape.db.reservation.dao.ReservationDao;
import roomescape.db.reservation.entity.ReservationEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationsService {

    private final ReservationDao reservationDao;

    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservations() {
        return reservationDao.getReservations().stream()
                .map(reservationsEntity -> ReservationResponse.of(
                        reservationsEntity.getId(),
                        reservationsEntity.getName(),
                        reservationsEntity.getDate(),
                        reservationsEntity.getTime().getTime()))
                .toList();
    }

    @Transactional
    public ReservationResponse createReservations(final String name, final String date, final Long timeId) {
        final ReservationEntity reservationEntity = reservationDao.createReservations(name, date, timeId);
        return ReservationResponse.of(
                reservationEntity.getId(),
                reservationEntity.getName(),
                reservationEntity.getDate(),
                reservationEntity.getTime().getTime()
        );
    }

    @Transactional
    public void deleteReservations(final long reservationId) {
        reservationDao.deleteReservations(reservationId);
    }
}
