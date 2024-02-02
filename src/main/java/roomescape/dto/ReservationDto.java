package roomescape.dto;

import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {
    @NonNull
    private String name;
    @NonNull
    private String date;
    @NonNull
    private String time;
   ;
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getName() {
        return name;
    }
}
