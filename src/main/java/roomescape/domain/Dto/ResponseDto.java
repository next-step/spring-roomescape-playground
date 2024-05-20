package roomescape.domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import roomescape.domain.Model.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        LocalDateTime reservationDateTime = reservation.getLocalDateTime();
        return new ResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservationDateTime.toLocalDate().toString(),
                reservationDateTime.toLocalTime().toString()
        );
    }

}