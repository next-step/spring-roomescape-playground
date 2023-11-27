package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private List<Reservation> reservations = new ArrayList<>();

    public List<ReservationResponse> getReservations() {
        return reservations.stream()
            .map(ReservationResponse::from)
            .collect(Collectors.toList());
    }
}
