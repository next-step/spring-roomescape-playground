package roomescape.reservation.dto;

import lombok.Data;
import roomescape.reservation.domain.Reservation;

@Data
public class ResponseDto {
    private Long id;
    private String name;
    private String date;
    private String time;

    public ResponseDto(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ResponseDto makeResponse(Reservation reservation) {
        return new ResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().toString()
        );
    }

}