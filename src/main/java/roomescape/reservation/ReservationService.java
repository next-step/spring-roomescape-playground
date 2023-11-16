package roomescape.reservation;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

  @Transactional
  public List<ReservationResponse> getReservationInfo() {

    List<Reservation> reservations = new ArrayList<>();
    List<ReservationResponse> reservationResponses = new ArrayList<>();

    Reservation reservation1 = Reservation.builder()
        .id(1L)
        .name("브라운")
        .date("2023-01-01")
        .time("10:00")
        .build();
    reservations.add(reservation1);

    Reservation reservation2 = Reservation.builder()
        .id(2L)
        .name("브라운")
        .date("2023-01-02")
        .time("11:00")
        .build();
    reservations.add(reservation2);

    Reservation reservation3 = Reservation.builder()
        .id(3L)
        .name("브라운")
        .date("2023-01-03")
        .time("12:00")
        .build();
    reservations.add(reservation3);

    for (Reservation reservation : reservations) {
      reservationResponses.add(ReservationResponse.from(reservation));
    }

    return reservationResponses;

  }

}
