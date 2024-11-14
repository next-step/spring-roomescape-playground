package roomescape.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

public record ReservationRequestDto(

    @NotBlank
    String name,

    @NotBlank
    String date,

    @NotNull
    @JsonFormat(shape = Shape.NUMBER_INT)
    Long time

) {

  public Reservation toReservation(Time time) {
    return Reservation.builder()
        .name(this.name())
        .date(this.date())
        .time(time)
        .build();
  }
}