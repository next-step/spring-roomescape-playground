package roomescape;

import org.thymeleaf.util.StringUtils;

public class ReservationRequestDto {
    private String name;
    private String date;
    private String time;

    public ReservationRequestDto (String name, String date, String time) {
        if(StringUtils.isEmpty(name)) {
            throw new BadRequestCreateReservationException("The 'name' field is missing.");
        }
        if(StringUtils.isEmpty(date)) {
            throw new BadRequestCreateReservationException("The 'date' field is missing.");
        }
        if(StringUtils.isEmpty(time)) {
            throw new BadRequestCreateReservationException("The 'time' field is missing.");
        }
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
