package roomescape.domain.reservation.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.reservationTime.domain.ReservationTime;
import roomescape.domain.reservationTime.repository.ReservationTimeRepository;
import roomescape.global.exception.RoomescapeBadRequestException;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.dto.ReservationRequest;
import roomescape.domain.reservation.dto.ReservationResponse;
import roomescape.domain.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(final ReservationRepository reservationRepository,
                              final ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> getReservationResponses() {

        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::fromReservation)
                .toList();
    }

    @Transactional
    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        if (reservationRequest.date().isBefore(LocalDate.now())) {
            throw new RoomescapeBadRequestException("잘못된 예약 날짜입니다. 현재 날짜보다 이전 날짜에 예약할 수 없습니다.");
        }

        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequest.time());
        if (reservationRequest.date().isEqual(LocalDate.now()) && reservationTime.isBefore()) {
            throw new RoomescapeBadRequestException("잘못된 예약 날짜입니다. 현재 시각 이전 시간에 예약할 수 없습니다.");
        }

        Reservation savedReservation = reservationRepository.create(reservationRequest.toReservation(reservationTime));

        return ReservationResponse.fromReservation(savedReservation);
    }

    @Transactional
    public void deleteReservation(final long id) {
        if (id < 0) {
            throw new RoomescapeBadRequestException("잘못된 룸 아이디입니다.");
        }

        reservationRepository.remove(id);
    }
}
