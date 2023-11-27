package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public List<ReservationResponse> getReservations() {
        return reservations.stream()
            .map(ReservationResponse::from)
            .collect(Collectors.toList());
    }

    public ReservationResponse createReservation(ReservationRequest reservation) {
        return ReservationResponse.from(index.incrementAndGet(), reservation);
    }
}
