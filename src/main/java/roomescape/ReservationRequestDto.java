package roomescape;

import org.thymeleaf.util.StringUtils;

public class ReservationRequestDto {
    private Long id;
    private String name;
    private String date;
    private Long time;

    public ReservationRequestDto(Long id, String name, String date, Long time) {
        if (StringUtils.isEmpty(name)) {
            throw new BadRequestCreateReservationException("The 'name' field is missing.");
        }
        if (StringUtils.isEmpty(date)) {
            throw new BadRequestCreateReservationException("The 'date' field is missing.");
        }
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public Long getId() {return id;}

    public Long getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
