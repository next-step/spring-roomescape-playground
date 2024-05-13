package roomescape.dto;

import lombok.Getter;
import lombok.Setter;
import roomescape.domain.Reservation;

@Setter
@Getter
public class ReservationResponseDto {

    public Long id;
    public String name;
    public String date;
    public String time;

    public static ReservationResponseDto from(Reservation reservation) {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setId(reservation.getId());
        dto.setName(reservation.getName());
        dto.setDate(reservation.getDate().toString());
        dto.setTime(reservation.getTime().toString());
        return dto;
    }
}
