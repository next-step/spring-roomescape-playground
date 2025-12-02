package roomescape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Time;

@Getter
@AllArgsConstructor
public class ReservationAddReq {
    private String name;
    private String date; //우선 String으로.. 차후 Date로 형변환 필요하면 교체..
    private Time time; //동일!!
}
