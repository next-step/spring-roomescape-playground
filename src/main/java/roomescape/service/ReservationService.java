package roomescape.service;

import java.util.ArrayList;
import java.util.List;

import roomescape.dto.Reservation;

public class ReservationService {
    private List<Reservation> reservations = new ArrayList<>();

    // 1-2 예약조회 테스트용
    {
        reservations.addAll(List.of(
            new Reservation(1, "브라운", "2023-01-01", "10:00"),
            new Reservation(2, "브라운", "2023-01-02", "11:00")
        ));
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}
