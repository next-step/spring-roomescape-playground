package roomescape.reservation.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import roomescape.reservation.Reservation;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class ReservationResponse {

  private Long id;
  private String name;
  private String date;
  private String time;

  public static ReservationResponse from(Reservation reservation) {
    return ReservationResponse.builder()
        .id(reservation.getId())
        .name(reservation.getName())
        .date(reservation.getDate())
        .time(reservation.getTime())
        .build();
  }

}
