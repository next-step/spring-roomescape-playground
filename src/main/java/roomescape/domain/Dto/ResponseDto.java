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

    public static ResponseDto makeResponse(Reservation reservation) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.id = reservation.getId();
        responseDto.name = reservation.getName();
        responseDto.date = reservation.getDate();
        responseDto.time = reservation.getTime();
        return responseDto;
    }

}