package roomescape.reservation;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape._core.errors.Exception.Exception400;
import roomescape.reservation.dto.ReservationResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final ReservationRepositoryJdbc reservationRepositoryJdbc;

  @Transactional
  public List<Reservation> getAllReservationInfo() {
//    List<ReservationResponse> reservationResponses = new ArrayList<>();
//
//    Reservation reservation1 = Reservation.builder().id(1L).name("브라운").date("2023-01-01").time("10:00").build();
//    reservationRepository.save(reservation1);
//
//    Reservation reservation2 = Reservation.builder().id(2L).name("브라운").date("2023-01-02").time("11:00").build();
//    reservationRepository.save(reservation2);
//
//    Reservation reservation3 = Reservation.builder().id(3L).name("브라운").date("2023-01-03").time("12:00").build();
//    reservationRepository.save(reservation3);
//
//    for (Reservation reservation : reservationRepository.findAll()) {
//      reservationResponses.add(ReservationResponse.from(reservation));
//    }

    return reservationRepositoryJdbc.findAll();

  }

  @Transactional
  public Reservation addReservation(String date, String name, Time time) {
//    if (date.isBlank() || name.isBlank() || time.isBlank()) {
//      throw new Exception400("올바른 예약 정보가 제공되지 않았습니다.");
//    }

//    Reservation reservation = Reservation.builder()
//        .id(reservationRepository.count() + 1)
//        .date(date)
//        .name(name)
//        .time(time)
//        .build();
//    reservationRepository.save(reservation);

    return reservationRepositoryJdbc.save(name, date, time);
  }

//  @Transactional
//  public Reservation getByReservationId(Long id) {
////    Reservation reservation = reservationRepository.findById(id).orElseThrow();
////    return ReservationResponse.from(reservation);
//    return reservationRepositoryJdbc.findById(id);
//  }

  @Transactional
  public void deleteReservation(Long id) {
//    Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new Exception400("예약 내역이 없습니다."));
//    reservationRepository.delete(reservation);
    reservationRepositoryJdbc.deleteById(id);
  }

}
