package roomescape.reservation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.exception.NotFoundReservationException;

@Service
public class ReservationService {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public List<ReservationResponse> getReservations() {
        return reservations.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ReservationResponse addReservation(ReservationRequest request) {
        if (isDuplicate(request)) {
            throw new IllegalArgumentException("이미 해당 날짜와 시간에 예약이 존재합니다.");
        }

        Long id = index.getAndIncrement();
        Reservation newReservation = new Reservation(id, request.name(), request.date(), request.time());
        reservations.add(newReservation);
        return toResponseDTO(newReservation);
    }

    public void deleteReservation(Long id) {
        Reservation target = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("해당 ID의 예약이 존재하지 않습니다: "));

        reservations.remove(target);
    }

    private ReservationResponse toResponseDTO(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    private boolean isDuplicate(ReservationRequest request) {
        return reservations.stream().anyMatch(reservation ->
                reservation.getDate().equals(request.date()) &&
                        reservation.getTime().equals(request.time())
        );
    }
}
