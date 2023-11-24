package roomescape.domain.reservation.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservation.repository.ReservationRepositoryJdbc;
import roomescape.global.BusinessException;
import roomescape.global.ErrorCode;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static roomescape.global.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepositoryJdbc repository;

    public List<Reservation> findAllReservations() {
        return repository.findAll();
    }

    @Transactional
    public Reservation saveReservation(final String name, final LocalDate date, final LocalTime time) {
        return repository.save(name, date, time);
    }

    public Reservation findReservation(final Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(id, "reservationId", RESERVATION_NOT_FOUND));
    }

    @Transactional
    public void deleteReservation(final Long id) {
        repository.deleteById(id);
    }

}
