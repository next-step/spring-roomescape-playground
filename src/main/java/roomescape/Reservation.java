package roomescape;

import org.thymeleaf.util.StringUtils;

public class Reservation {
    private long id;
    private String name;
    private String date;
    private String time;

    public Reservation(long id, String name, String date, String time) {
        if(StringUtils.isEmpty(name)) {
            throw new BadRequestCreateReservationException("The 'name' field is missing.");
        }
        if(StringUtils.isEmpty(date)) {
            throw new BadRequestCreateReservationException("The 'date' field is missing.");
        }
        if(StringUtils.isEmpty(time)) {
            throw new BadRequestCreateReservationException("The 'time' field is missing.");
        }
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
