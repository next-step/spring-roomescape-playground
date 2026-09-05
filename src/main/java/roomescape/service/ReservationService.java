package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;
    private static final int NO_ROWS = 0;

    public ReservationService(
            ReservationRepository reservationRepository,
            TimeRepository timeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundReservationException("조회할 예약을 찾을 수 없습니다."));
    }

    private Time findTimeById(Long id) {
        return timeRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundTimeException("예약 시간을 찾을 수 없습니다."));
    }

    public Reservation save(ReservationRequest request) {
        if (request.time() == null) {
            throw new InvalidReservationException("예약 시간을 선택해야 합니다.");
        }

        Time time = findTimeById(request.time());
        Reservation reservation = request.toEntity(time);

        return reservationRepository.save(reservation);
    }

    public void deleteById(Long id) {
        int deletedCount = reservationRepository.deleteById(id);

        if (deletedCount == NO_ROWS) {
            throw new NotFoundReservationException("삭제할 예약을 찾을 수 없습니다.");
        }
    }
}
