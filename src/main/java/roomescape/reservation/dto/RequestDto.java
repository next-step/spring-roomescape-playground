package roomescape.reservation.dto;

import lombok.Data;

@Data
public class RequestDto {
    private String name;
    private String date;
    private Long time;
}