package roomescape.domain.reservation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.global.exception.RoomescapeBadRequestException;
import roomescape.global.exception.RoomescapeNotFoundException;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.dto.ReservationRequest;
import roomescape.domain.reservation.dto.ReservationResponse;
import roomescape.domain.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationResponses() {

        return reservationRepository.getReservations()
                .stream()
                .map(ReservationResponse::fromReservation)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        if (reservationRequest.date().isBefore(LocalDate.now())) {
            throw new RoomescapeBadRequestException("잘못된 예약 날짜입니다. 현재 날짜보다 이전 날짜에 예약할 수 없습니다.");
        }
        if (reservationRequest.date().isEqual(LocalDate.now()) && reservationRequest.time().isBefore(LocalTime.now())) {
            throw new RoomescapeBadRequestException("잘못된 예약 날짜입니다. 현재 시작 이전 시간에 예약할 수 없습니다.");
        }
        Reservation savedReservation = reservationRepository.addReservation(reservationRequest.newReservation());

        return ReservationResponse.fromReservation(savedReservation);
    }

    @Transactional
    public void deleteReservation(final long id) {
        if (id < 0) {
            throw new RoomescapeBadRequestException("잘못된 룸 아이디입니다.");
        }

        boolean removed = reservationRepository.removeReservation(id);

        if (!removed) {
            throw new RoomescapeNotFoundException("존재하지 않은 예약입니다.");
        }
    }
}
