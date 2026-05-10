package roomescape.dto;

import lombok.Getter;

@Getter
public class ReservationRequest {
    private String name;
    private String date;
    private String time;
}
