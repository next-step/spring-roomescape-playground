package roomescape.reservation;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
