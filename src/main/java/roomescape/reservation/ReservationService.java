package roomescape.reservation;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.dto.ReservationResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

  private final ReservationRepository reservationRepository;

  @Transactional
  public List<ReservationResponse> getReservationInfo() {
    List<ReservationResponse> reservationResponses = new ArrayList<>();

    Reservation reservation1 = Reservation.builder()
        .id(1L)
        .name("브라운")
        .date("2023-01-01")
        .time("10:00")
        .build();
    reservationRepository.save(reservation1);

    Reservation reservation2 = Reservation.builder()
        .id(2L)
        .name("브라운")
        .date("2023-01-02")
        .time("11:00")
        .build();
    reservationRepository.save(reservation2);

    Reservation reservation3 = Reservation.builder()
        .id(3L)
        .name("브라운")
        .date("2023-01-03")
        .time("12:00")
        .build();
    reservationRepository.save(reservation3);

    for (Reservation reservation : reservationRepository.findAll()) {
      reservationResponses.add(ReservationResponse.from(reservation));
    }

    return reservationResponses;

  }

  @Transactional
  public ReservationResponse addReservation(String date, String name, String time) {
    Reservation reservation = Reservation.builder()
        .id(reservationRepository.count() + 1)
        .date(date)
        .name(name)
        .time(time)
        .build();
    reservationRepository.save(reservation);

    return ReservationResponse.from(reservation);
  }

  @Transactional
  public ReservationResponse getByReservationId(Long id) {
    Reservation reservation = reservationRepository.findById(id).orElseThrow();
    return ReservationResponse.from(reservation);
  }

  @Transactional
  public void deleteReservation(Long id) {
    reservationRepository.deleteById(id);
  }

}
