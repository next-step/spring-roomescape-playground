package roomescape.domain.Dto.reservationDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import roomescape.domain.Model.Reservation;
import roomescape.domain.Model.Time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
public class RequestDto {
    private String name;
    private String date;
    private Long time;
}