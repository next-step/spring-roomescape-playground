package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.JdbcTemplateReservationRepository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.exception.ReservationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final JdbcTemplateReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(JdbcTemplateReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation newReservation = Reservation.create(
                request.name(), request.parseDate(), request.parseTime()
        );
        Reservation storedReservation = reservationRepository.save(newReservation);
        return ReservationResponse.from(storedReservation);
    }

    public List<ReservationResponse> findAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReservation(Long id) {
        int affectedRows = reservationRepository.deleteById(id);
        if (affectedRows == 0) {
            throw new ReservationException("[ERROR] 삭제하려는 예약을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public ReservationTimeResponse createTime(ReservationTimeRequest request) {
        ReservationTime newReservationTime = ReservationTime.from(request.time());
        ReservationTime savedReservationTime = reservationTimeRepository.save(newReservationTime);
        return ReservationTimeResponse.from(savedReservationTime);
    }

    public List<ReservationTimeResponse> findAllTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTime(Long id) {
        int affectedRows = reservationTimeRepository.deleteById(id);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("[ERROR] 삭제하려는 시간을 찾을 수 없습니다.");
        }
    }
}
