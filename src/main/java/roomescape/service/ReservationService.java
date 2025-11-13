package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final List<Reservation> reservationList = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(0);

    public List<ReservationResponse> getAllReservations() {
        return reservationList.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        validateReservationRequest(request);
        Long newId = index.incrementAndGet();

        Reservation newReservation = new Reservation(
                newId,
                request.name(),
                LocalDate.parse(request.date()),
                LocalTime.parse(request.time())
        );

        reservationList.add(newReservation);
        return ReservationResponse.from(newReservation);
    }

    public void deleteReservation(Long id) {
        boolean removed = reservationList.removeIf(reservation -> reservation.getId().equals(id));

        if (!removed) {
            throw new NotFoundReservationException("예약을 찾을 수 없습니다");
        }
    }

    private void validateReservationRequest(ReservationRequest request) {
        if (request.name() == null || request.name().isEmpty()) {
            throw new BadRequestReservationException("이름은 필수 항목입니다.");
        }
        if (request.date() == null || request.date().isEmpty()) {
            throw new BadRequestReservationException("날짜는 필수 항목입니다.");
        }
        if (request.time() == null || request.time().isEmpty()) {
            throw new BadRequestReservationException("시간은 필수 항목입니다.");
        }
    }
}
