package roomescape.entity.Dto;

public class ReservationInDto {

    private String name;
    private String date;
    private Long time;

    public ReservationInDto(String name, String date, Long time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getTimeId() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
